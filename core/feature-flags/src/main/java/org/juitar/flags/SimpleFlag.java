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

    public SimpleFlag(String name, FlagGroup group, boolean on) {
        super(name, group);
        this.on = on;
    }


    @Override
    protected final boolean isFlagOn(Scope scope) {
        return on;
    }

    @Override
    protected final boolean isFlagOn() {
        return on;
    }

    @Override
    public final boolean isScoped() {
        return false;
    }
}
