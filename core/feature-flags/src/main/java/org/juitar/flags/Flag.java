package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public abstract class Flag extends Identified {

    protected Flag(String name) {
        super(name);
    }

    public final String getName() {
        return id;
    }

    @Override
    public String toString() {
        return "flag:" + id;
    }

    public abstract boolean isOn();

    public abstract boolean isOn(Scope scope);

    public abstract boolean isScoped();
}
