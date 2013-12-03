package org.juitar.flags;

import java.util.Set;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public class Flags {

    private static final Registry<Flag> FLAG_REGISTRY = new Registry<>();

    public static Flag get(String flag) {
        return FLAG_REGISTRY.get(flag);
    }

    public static Flag register(Flag flag) {
        return FLAG_REGISTRY.register(flag);
    }

    public static Flag register(String name, boolean on) {
        return FLAG_REGISTRY.register(new SimpleFlag(name, on));
    }

    public static Flag register(String name, Set<Scope> scopes) {
        return FLAG_REGISTRY.register(new ScopedFlag(name, scopes));
    }

    public static Flag register(String name, Set<Scope> scopes, boolean inclusive) {
        return FLAG_REGISTRY.register(new ScopedFlag(name, scopes, inclusive));
    }
}
