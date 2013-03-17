package junitar.server.netty.jersey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sha1n
 * Date: 3/17/13
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    QueryParams[] failIfEmpty() default {QueryParams.LAYOUT, QueryParams.FILTER};
}
