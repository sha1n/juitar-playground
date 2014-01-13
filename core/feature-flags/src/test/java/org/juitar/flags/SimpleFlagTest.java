package org.juitar.flags;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sha1n
 * Date: 12/3/13
 */
public class SimpleFlagTest {

    @Test
    public void testIsOn() {
        Assert.assertTrue(new SimpleFlag("test", true).isOn());
    }

    @Test
    public void testIsOff() {
        Assert.assertFalse(new SimpleFlag("test", false).isOn());
    }

    @Test
    public void testIsOnWithOffGroup() {
        Assert.assertFalse(new SimpleFlag("test", new FlagGroupImpl("testGroup", false), true).isOn());
    }

    @Test
    public void testIsOnWhenOffWithOnGroup() {
        Assert.assertFalse(new SimpleFlag("test", new FlagGroupImpl("testGroup", true), false).isOn());
    }
}
