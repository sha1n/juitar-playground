package org.juitar.infra.netty.client;

/**
 * @author sha1n
 * Date: 11/27/13
 */
public enum HttpMethod {

    POST(io.netty.handler.codec.http.HttpMethod.POST),
    GET(io.netty.handler.codec.http.HttpMethod.GET),
    PUT(io.netty.handler.codec.http.HttpMethod.PUT),
    DELETE(io.netty.handler.codec.http.HttpMethod.DELETE);

    final io.netty.handler.codec.http.HttpMethod nettyMethod;

    private HttpMethod(io.netty.handler.codec.http.HttpMethod nettyMethod) {
        this.nettyMethod = nettyMethod;
    }
}
