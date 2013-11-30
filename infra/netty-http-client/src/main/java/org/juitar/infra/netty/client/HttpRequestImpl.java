package org.juitar.infra.netty.client;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author sha1n
 * Date: 11/27/13
 */
public class HttpRequestImpl implements HttpRequest {

    private final HttpMethod httpMethod;
    private final String uri;
    private final ResponseHandler responseHandler;
    private final HttpConnectionPool connectionPool;
    private HttpConnection connection;
    private boolean connecting = false;
    private final io.netty.handler.codec.http.HttpRequest nettyRequest;

    public HttpRequestImpl(HttpMethod httpMethod, String uri, HttpConnectionPool connectionPool, ResponseHandler responseHandler) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.connectionPool = connectionPool;
        this.responseHandler = responseHandler;
        String hostname = connectionPool.getHostname();
        int port = connectionPool.getPort();
        String baseUrl = "http://" + hostname + ":" + port;
        this.nettyRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod.nettyMethod, uri);
    }

    public Headers getHeaders() {
        return new NettyHeadersWrapper(nettyRequest.headers());
    }

    public void commit() {

        Runnable action = new Runnable() {
            @Override
            public void run() {
                connection.channel.writeAndFlush(nettyRequest);
                // Wait for the server to close the connection.
                try {
                    connection.channel.closeFuture().sync();
                } catch (InterruptedException e) {
                    // TODO report this
                    e.printStackTrace();
                }

            }
        };

        if (connection == null) {
            connect(action);
        } else {
            action.run();
        }

    }

    private void connect(final Runnable action) {
        if (!connecting) {

            connecting = true;
            connectionPool.connect(new ConnectRequest() {
                @Override
                public void connected(HttpConnection httpConnection) {
                    if (httpConnection.isOpen()) {
                        connection = httpConnection;
                        connection.setResponseHandler(responseHandler);
                        action.run();
                    } else {
                        // Try another one
                        connecting = false;
                        connect(action);
                    }
                }

                @Override
                public void failed(Throwable cause) {
                    connectionFailed(cause);
                }
            });

        }
    }

    private void connectionFailed(Throwable cause) {
        // TODO handle connection failure
    }
}
