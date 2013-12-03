package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public abstract class Flag extends Identified {

    private final FlagGroup group;

    protected Flag(String name) {
        this(name, NullFlagGroup.NULL_FLAG_GROUP);
    }

    protected Flag(String name, FlagGroup group) {
        super(name);
        this.group = group;
    }


    public final String getName() {
        return id;
    }

    @Override
    public String toString() {
        return "flag:" + id;
    }

    public final boolean isOn() {
        return group.isOn() && isFlagOn();
    }

    public final boolean isOn(Scope scope) {
        return group.isOn() && isFlagOn(scope);
    }

    protected abstract boolean isFlagOn();

    protected abstract boolean isFlagOn(Scope scope);

    public abstract boolean isScoped();
}
