package org.juitar.commons.l10n;

import java.lang.annotation.*;

/**
 * @author sha1n
 * Date: 2/15/13
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface L10nBundle {

    String value();
}
