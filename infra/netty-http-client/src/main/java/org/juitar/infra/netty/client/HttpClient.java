package org.juitar.infra.netty.client;

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
        return new HttpRequestImpl(HttpMethod.GET, uri, responseHandler);
    }
}
