package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public class SimpleFlag extends Flag {

    private final boolean on;

    public SimpleFlag(String name, boolean on) {
        super(name);
        this.on = on;
    }

    @Override
    public final boolean isOn(Scope scope) {
        return on;
    }

    @Override
    public final boolean isOn() {
        return on;
    }

    @Override
    public final boolean isScoped() {
        return false;
    }
}
