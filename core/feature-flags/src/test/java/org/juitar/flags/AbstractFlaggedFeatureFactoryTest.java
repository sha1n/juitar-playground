package org.juitar.flags;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author sha1n
 *         Date: 12/3/13
 */
public class AbstractFlaggedFeatureFactoryTest {

    AtomicBoolean flagOnCalled = new AtomicBoolean(false);
    AtomicBoolean flagOffCalled = new AtomicBoolean(false);

    @Before
    public void setup() {
        Flags.register("on", true);
        Flags.register("off", false);
        HashSet<Scope> scopes = new HashSet<>();
        scopes.add(Scopes.register("scope1"));
        scopes.add(Scopes.register("scope2"));
        Flags.register("scopedInc", scopes, true);
        Flags.register("scopedExc", scopes, false);

        Scopes.register("scope4");
    }

    @Test
    public void testGetInstanceArgScopeUnScopedOn() {
        doGetInstanceArgScope(Flags.get("on"), Scopes.get("new"), true, false);
    }

    @Test
    public void testGetInstanceArgScopeUnScopedOff() {
        doGetInstanceArgScope(Flags.get("off"), Scopes.get("new"), false, true);
    }

    @Test
    public void testGetInstanceArgScopeScopedIncOn() {
        doGetInstanceArgScope(Flags.get("scopedInc"), Scopes.get("scope1"), true, false);
    }

    @Test
    public void testGetInstanceArgScopeScopedIncOff() {
        doGetInstanceArgScope(Flags.get("scopedInc"), Scopes.get("scope4"), false, true);
    }

    @Test
    public void testGetInstanceArgScopeScopedExcOn() {
        doGetInstanceArgScope(Flags.get("scopedExc"), Scopes.get("scope4"), true, false);
    }

    @Test
    public void testGetInstanceArgScopeScopedExcOff() {
        doGetInstanceArgScope(Flags.get("scopedExc"), Scopes.get("scope1"), false, true);
    }

    @Test
    public void testGetInstanceArgUnScopedOn() {
        doGetInstanceArg(Flags.get("on"), true, false);
    }

    @Test
    public void testGetInstanceArgUnScopedOff() {
        doGetInstanceArg(Flags.get("off"), false, true);
    }

    @Test
    public void testGetInstanceArgScopedIncNoScope() {
        doGetInstanceArg(Flags.get("scopedInc"), false, true);
    }

    @Test
    public void testGetInstanceArgScopedExcNoScope() {
        doGetInstanceArg(Flags.get("scopedExc"), false, true);
    }

    private void doGetInstanceArgScope(Flag flag, Scope scope, boolean onCalled, boolean offCalled) {

        AbstractFlaggedFeatureFactory<FlaggedFeature, String> factory = getTestObject(flag);

        factory.getInstance(null, scope);
        Assert.assertTrue(flagOnCalled.get() == onCalled);
        Assert.assertTrue(flagOffCalled.get() == offCalled);
    }

    private void doGetInstanceArg(Flag flag, boolean onCalled, boolean offCalled) {

        AbstractFlaggedFeatureFactory<FlaggedFeature, String> factory = getTestObject(flag);

        factory.getInstance(null);
        Assert.assertTrue(flagOnCalled.get() == onCalled);
        Assert.assertTrue(flagOffCalled.get() == offCalled);
    }

    private AbstractFlaggedFeatureFactory<FlaggedFeature, String> getTestObject(final Flag flag) {
        return new AbstractFlaggedFeatureFactory<FlaggedFeature, String>(flag) {

                @Override
                protected FlaggedFeature getFeatureFlagOn(String arg) {
                    flagOnCalled.set(true);
                    return null;
                }

                @Override
                protected FlaggedFeature getFeatureFlagOff(String arg) {
                    flagOffCalled.set(true);
                    return null;
                }
            };
    }
}
