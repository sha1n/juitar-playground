package org.juitar.infra.netty.client;

/**
 * @author sha1n
 * Date: 12/1/13
 */
public class ClassUtil {

    public static <T> T tryCast(Object obj, Class<T> cls) {
        if (cls.isInstance(obj)) {
            return cls.cast(obj);
        } else {
            return null;
        }
    }
}
