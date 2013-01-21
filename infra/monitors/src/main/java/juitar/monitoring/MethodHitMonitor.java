package juitar.monitoring;

import juitar.context.Context;
import juitar.monitoring.api.Monitored;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author sha1n
 * Date: 1/4/13
 */
public class MethodHitMonitor extends AbstractMethodMonitor {

    private AtomicLong globalHitCount = new AtomicLong(0);
    private AtomicLong averageHitsPerSecond = new AtomicLong(0);

    @Override
    protected void internBefore(Monitored monitored, Context ctx) {
    }

    @Override
    protected void internAfter(Monitored monitored, Context ctx) {
    }
}
