package juitar.monitoring.spi.config;

import org.juitar.monitoring.spi.config.MonitorConfiguration;
import org.juitar.monitoring.spi.config.MonitorConfigurationProvider;

/**
 * @author sha1n
 * Date: 1/28/13
 */
public class MonitorConfigurationProviderImpl implements MonitorConfigurationProvider {

    @Override
    public MonitorConfiguration getMonitorConfiguration(String s) {
        return new MonitorConfiguration() {
            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
