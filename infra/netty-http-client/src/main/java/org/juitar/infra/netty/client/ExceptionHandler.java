package org.juitar.infra.netty.client;

/**
 * @author sha1n
 * Date: 11/25/13
 */
public interface ExceptionHandler {

    void handle(Throwable t);
}
