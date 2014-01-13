package org.juitar.flags;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author sha1n
 * Date: 12/2/13
 */
class Registry<T extends Identified> {

    private final ConcurrentMap<String, T> registry = new ConcurrentHashMap<>();

    final T get(String obj, T defaultObject) {
        T t = get(obj);

        if (t == null) {
            t = defaultObject;
        }

        return t;
    }

    final T get(String obj) {
        return registry.get(obj);
    }

    final T register(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Cannot register 'null'");
        }

        registry.put(object.id, object);
        return object;
    }

}
