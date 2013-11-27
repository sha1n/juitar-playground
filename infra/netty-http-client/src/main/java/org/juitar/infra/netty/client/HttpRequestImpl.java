package org.juitar.infra.netty.client;

/**
 * @author sha1n
 * Date: 11/27/13
 */
public class HttpRequestImpl implements HttpRequest {

    private final HttpMethod httpMethod;
    private final Headers headers = new HeadersImpl();
    private final String uri;
    private final ResponseHandler responseHandler;
    private final HttpConnectionPool connectionPool;
    private HttpConnection connection;
    private boolean connecting = false;

    public HttpRequestImpl(HttpMethod httpMethod, String uri, HttpConnectionPool connectionPool, ResponseHandler responseHandler) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.connectionPool = connectionPool;
        this.responseHandler = responseHandler;
    }

    public Headers getHeaders() {
        return headers;
    }

    private void connect() {
        if (!connecting) {

            connecting = true;
            connectionPool.connect(new ConnectRequest() {
                @Override
                public void connected(HttpConnection httpConnection) {
                    if (httpConnection.isOpen()) {
                        connection = httpConnection;
                    } else {
                        // Try another one
                        connecting = false;
                        connect();
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
