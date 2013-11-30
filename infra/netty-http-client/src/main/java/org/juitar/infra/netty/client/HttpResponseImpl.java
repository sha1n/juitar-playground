package org.juitar.infra.netty.client;

/**
 * @author sha1n
 * Date: 11/30/13
 */
public class HttpResponseImpl implements HttpResponse {

    private final HttpConnection httpConnection;
    private final io.netty.handler.codec.http.HttpResponse nettyResponse;

    HttpResponseImpl(HttpConnection httpConnection, io.netty.handler.codec.http.HttpResponse nettyResponse) {
        this.httpConnection = httpConnection;
        this.nettyResponse = nettyResponse;

        nettyResponse.getStatus().code();
    }

    @Override
    public int getStatusCode() {
        return nettyResponse.getStatus().code();
    }

    @Override
    public String getStatusLine() {
        return nettyResponse.getStatus().reasonPhrase();
    }
}
