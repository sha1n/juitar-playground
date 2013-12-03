package org.juitar.flags;

/**
 * @author sha1n
 *         Date: 12/3/13
 */
public class SystemPropertyFlag extends Flag {


    protected SystemPropertyFlag(String name) {
        super(name);
    }

    @Override
    public boolean isOn() {
        return System.getProperty(id) != null;
    }

    @Override
    public boolean isOn(Scope scope) {
        return isOn();
    }

    @Override
    public boolean isScoped() {
        return false;
    }
}
