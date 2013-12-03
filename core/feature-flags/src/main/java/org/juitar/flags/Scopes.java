package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public class Scopes {
    private static final Registry<Scope> SCOPE_REGISTRY = new Registry<>();
    private static final Scope NULL = new Scope("null");


    public static Scope get(String name) {
        return SCOPE_REGISTRY.get(name, NULL);
    }

    public static Scope register(Scope scope) {
        return SCOPE_REGISTRY.register(scope);
    }

    public static Scope register(String scope) {
        return SCOPE_REGISTRY.register(new Scope(scope));
    }
}
