package org.juitar.flags;

/**
 * @author sha1n
 *         Date: 12/3/13
 */
public class SystemPropertyFlag extends Flag {


    protected SystemPropertyFlag(String name) {
        super(name);
    }

    protected SystemPropertyFlag(String name, FlagGroup group) {
        super(name, group);
    }

    @Override
    protected final boolean isFlagOn() {
        return System.getProperty(id) != null;
    }

    @Override
    protected final boolean isFlagOn(Scope scope) {
        return isOn();
    }

    @Override
    public boolean isScoped() {
        return false;
    }
}
