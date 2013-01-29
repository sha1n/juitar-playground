package juitar.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PreDestroy;

/**
 * @author sha1n
 * Date: 1/26/13
 */
class ContextAccessInitializer implements ApplicationContextAware {

    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) {
        ContextAccess.setApplicationContext(applicationContext);
    }

    @PreDestroy
    public final void destroy() {
        ContextAccess.setApplicationContext(null);
    }
}
