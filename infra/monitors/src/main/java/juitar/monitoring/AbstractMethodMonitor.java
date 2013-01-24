package juitar.monitoring;

import juitar.context.Context;
import juitar.context.ContextAccess;
import juitar.monitoring.api.Monitored;
import juitar.monitoring.spi.MethodMonitor;

/**
 * @author sha1n
 * Date: 1/4/13
 */
abstract class AbstractMethodMonitor implements MethodMonitor {

    protected abstract void internBefore(Monitored monitored, Context ctx);

    protected abstract void internAfter(Monitored monitored, Context ctx);

    @Override
    public final void before(Monitored monitored) {
        Context context = new ContextAccess().getContext();
//        System.out.println("Before " + context.getCorrelationId());

        internBefore(monitored, context);
    }

    @Override
    public final void after(Monitored monitored) {
        Context context = new ContextAccess().getContext();

        internAfter(monitored, context);

//        System.out.println("After " + context.getCorrelationId());
    }
}
