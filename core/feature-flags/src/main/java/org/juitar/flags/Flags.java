package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public class Flags {

    private static final Registry<Flag> FLAG_REGISTRY = new Registry<>();

    public static Flag get(String flag) {
        return FLAG_REGISTRY.get(flag);
    }

    public static void register(Flag flag) {
        FLAG_REGISTRY.register(flag);
    }
}
