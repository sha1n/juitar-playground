package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public class Scopes {
    private static final Registry<Scope> FLAG_REGISTRY = new Registry<>();

    public static Scope get(String scope) {
        return FLAG_REGISTRY.get(scope);
    }

    public static void register(Scope scope) {
        FLAG_REGISTRY.register(scope);
    }
}
