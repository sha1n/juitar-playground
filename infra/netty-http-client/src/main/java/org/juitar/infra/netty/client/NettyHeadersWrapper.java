package org.juitar.infra.netty.client;

import io.netty.handler.codec.http.HttpHeaders;

import java.util.List;

/**
 * @author sha1n
 * Date: 11/27/13
 */
class NettyHeadersWrapper implements Headers {

    private final HttpHeaders nettyHeader;

    NettyHeadersWrapper(HttpHeaders headers) {
        this.nettyHeader = headers;
    }

    @Override
    public String get(String name) {
        return nettyHeader.get(name);
    }

    @Override
    public List<String> getAll(String name) {
        return nettyHeader.getAll(name);
    }

    @Override
    public void add(String name, String value) {
        nettyHeader.add(name, value);
    }

    @Override
    public void addAll(String name, List<String> values) {
        nettyHeader.add(name, values);
    }

}
