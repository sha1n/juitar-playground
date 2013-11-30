package org.juitar.infra.netty.client;

import java.io.IOException;

/**
 * @author sha1n
 * Date: 11/27/13
 */
public class HttpClient {

    private final HttpConnectionPool connectionPool;

    public HttpClient(int maxSize, String hostname, int port) {
        this.connectionPool = new HttpConnectionPool(maxSize, hostname, port);
        this.connectionPool.init();
    }

    public HttpRequest get(String uri, ResponseHandler responseHandler) {
        return new HttpRequestImpl(HttpMethod.GET, uri, connectionPool, responseHandler);
    }

    public void close() {
        try {
            connectionPool.close();
        } catch (IOException e) {
            // TODO report this
            e.printStackTrace();
        }
    }
}
