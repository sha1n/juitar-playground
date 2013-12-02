package org.juitar.flags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public abstract class AbstractFlaggedFeatureFactory<T extends FlaggedFeature, A> implements FlaggedFeatureFactory<T, A> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractFlaggedFeatureFactory.class);

    private final Flag flag;

    protected AbstractFlaggedFeatureFactory(Flag flag) {
        if (flag == null) {
            throw new IllegalArgumentException("flag is mandatory");
        }
        this.flag = flag;
    }

    protected AbstractFlaggedFeatureFactory(String flag) {
        this(Flags.get(flag));
    }

    protected abstract T getFeatureFlagOn(A arg);

    protected abstract T getFeatureFlagOff(A arg);

    @Override
    public final T getInstance(A arg, Scope scope) {
        LOGGER.info("Getting feature instance for flag: " + flag.getName() + " in " + scope);

        return getFlaggedFeature(arg, scope);
    }

    @Override
    public final T getInstance(A arg) {
        LOGGER.info("Getting feature instance for flag: " + flag.getName());

        return getFlaggedFeature(arg, null);
    }

    private T getFlaggedFeature(A arg, Scope scope) {
        T feature;
        if (flag.isOn(scope)) {
            feature = getFeatureFlagOn(arg);
        } else {
            feature = getFeatureFlagOff(arg);
        }

        return feature;
    }

}
