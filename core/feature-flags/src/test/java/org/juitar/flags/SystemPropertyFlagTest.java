package org.juitar.flags;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sha1n
 *         Date: 12/3/13
 */
public class SystemPropertyFlagTest {

    public static final String UNSET = "test1";
    public static final String SET = "test";
    SystemPropertyFlag existing = new SystemPropertyFlag(SET);
    SystemPropertyFlag notExisting = new SystemPropertyFlag(UNSET);

    @Before
    public void setup() {
        System.setProperty(SET, "");
    }

    @Test
    public void testExistingOn() {
        Assert.assertTrue(existing.isOn());
    }

    @Test
    public void testExistingOnScoped() {
        Assert.assertTrue(existing.isOn(Scopes.register("scope")));
    }

    @Test
    public void testExistingScoped() {
        Assert.assertFalse(existing.isScoped());
    }

    @Test
    public void testNotExistingOn() {
        Assert.assertFalse(notExisting.isOn());
    }

    @Test
    public void testNotExistingOnScoped() {
        Assert.assertFalse(notExisting.isOn(Scopes.register("scope")));
    }

    @Test
    public void testNotExistingScoped() {
        Assert.assertFalse(notExisting.isScoped());
    }
}
