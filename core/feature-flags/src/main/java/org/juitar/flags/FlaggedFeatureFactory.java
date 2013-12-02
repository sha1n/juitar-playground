package org.juitar.flags;

/**
 * @author sha1n
 * Date: 12/2/13
 */
public interface FlaggedFeatureFactory<T extends FlaggedFeature, A> {

    T getInstance(A arg, Scope scope);

    T getInstance(A arg);
}
