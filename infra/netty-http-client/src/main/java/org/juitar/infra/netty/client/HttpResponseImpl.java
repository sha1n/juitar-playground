package org.juitar.infra.netty.client;

/**
 * @author sha1n
 * Date: 11/30/13
 */
public class HttpResponseImpl implements HttpResponse {

    private final HttpConnection httpConnection;
    private final io.netty.handler.codec.http.HttpResponse nettyResponse;
    private HttpResponseContent content;

    HttpResponseImpl(HttpConnection httpConnection, io.netty.handler.codec.http.HttpResponse nettyResponse) {
        this.httpConnection = httpConnection;
        this.nettyResponse = nettyResponse;
    }

    public HttpResponseContent getContent() {
        return content;
    }

    public void setContent(HttpResponseContent content) {
        this.content = content;
    }


    @Override
    public int getStatusCode() {
        return nettyResponse.getStatus().code();
    }

    @Override
    public String getStatusLine() {
        return nettyResponse.getStatus().reasonPhrase();
    }

    @Override
    public String getBodyAsString() {
        return content.asString("UTF-8");
    }

}
