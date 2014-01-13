package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/3/13
 */
public class NullFlagGroup implements FlagGroup {

    public static final FlagGroup NULL_FLAG_GROUP = new NullFlagGroup();

    @Override
    public final boolean isOn() {
        return true;
    }
}
