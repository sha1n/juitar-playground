package org.juitar.infra.netty.client;

import java.util.List;

/**
 * @author sha1n
 * Date: 11/27/13
 */
public interface Headers {
    String get(String name);

    List<String> getAll(String name);

    void add(String name, String value);

    void addAll(String name, List<String> values);
}
