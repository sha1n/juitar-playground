package juitar.monitoring;

import juitar.context.Context;
import juitar.monitoring.api.Monitored;

/**
 * @author sha1n
 * Date: 1/4/13
 */
public class MethodTimeMonitor extends AbstractMethodMonitor {

    private long start = 0L;


    @Override
    protected void internBefore(Monitored monitored, Context ctx) {
        this.start = System.currentTimeMillis();
    }

    @Override
    protected void internAfter(Monitored monitored, Context ctx) {
        long elapsed = System.currentTimeMillis() - start;
        if (monitored.threshold() < elapsed) {
            throw new TimeoutException(
                    "Threshold '" + monitored.threshold() + "' exceeded. Elapsed=" + elapsed);
        }
//        System.out.println("Elapsed: " + elapsed);
    }

}
