package juitar.monitoring.spi;

import juitar.monitoring.api.Monitored;

/**
 * @author sha1n
 * Date: 1/4/13
 */
public interface MethodMonitor {

    void before(Monitored monitored);

    void after(Monitored monitored);
}
