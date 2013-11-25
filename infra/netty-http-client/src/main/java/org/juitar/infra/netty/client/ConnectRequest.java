package org.juitar.infra.netty.client;

/**
 * @author sha1n
 * Date: 11/25/13
 */
public interface ConnectRequest {

    void connected(HttpConnection httpConnection);

    void failed(Throwable cause);
}
