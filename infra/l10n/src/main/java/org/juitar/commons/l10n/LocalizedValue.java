package org.juitar.commons.l10n;

import java.lang.annotation.*;

/**
 * @author sha1n
 * Date: 2/15/13
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalizedValue {

    String resource() default "";

    String key();

}
