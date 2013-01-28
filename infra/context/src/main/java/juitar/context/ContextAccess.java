package juitar.context;

import org.springframework.context.ApplicationContext;

/**
 * @author sha1n
 * Date: 1/4/13
 */
public class ContextAccess implements org.juitar.monitoring.spi.context.ContextAccess {

    private static ApplicationContext applicationContext = null;

    static void setApplicationContext(ApplicationContext applicationContext) {
        if (ContextAccess.applicationContext != null && applicationContext != null) {
            throw new UnsupportedOperationException("Context already set.");
        }
        ContextAccess.applicationContext = applicationContext;
    }

    public ContextAccess() {
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private static final InheritableThreadLocal<Context> localContext = new InheritableThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new ContextImpl();
        }
    };

    public org.juitar.monitoring.spi.context.Context get() {
        return localContext.get();
    }
}
