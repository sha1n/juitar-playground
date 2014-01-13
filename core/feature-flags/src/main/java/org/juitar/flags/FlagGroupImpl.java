package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/3/13
 */
public class FlagGroupImpl extends Identified implements FlagGroup {

    private volatile boolean on;

    public FlagGroupImpl(String name, boolean on) {
        super(name);
        this.on = on;
    }

    public final boolean isOn() {
        return on;
    }

    public final void setOn(boolean on) {
        this.on = on;
    }
}
