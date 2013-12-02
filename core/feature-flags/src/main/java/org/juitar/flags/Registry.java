package org.juitar.flags;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author sha1n
 * Date: 12/2/13
 */
class Registry<T extends Identified> {

    private final ConcurrentMap<String, T> registry = new ConcurrentHashMap<>();

    public final T get(String flag) {
        return registry.get(flag);
    }

    final void register(T object) {
        registry.put(object.id, object);
    }
}
