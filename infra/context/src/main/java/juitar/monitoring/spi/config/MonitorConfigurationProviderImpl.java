package juitar.monitoring.spi.config;

import org.juitar.monitoring.spi.config.MonitorConfiguration;
import org.juitar.monitoring.spi.config.MonitorConfigurationProvider;

/**
 * @author sha1n
 * Date: 1/28/13
 */
public class MonitorConfigurationProviderImpl implements MonitorConfigurationProvider {

    @Override
    public MonitorConfiguration getDomainConfiguration(String s) {
        return getEnabledMonitorConfig();
    }

    @Override
    public MonitorConfiguration getCategoryConfiguration(String s) {
        return getEnabledMonitorConfig();

    }

    @Override
    public MonitorConfiguration getOperationConfiguration(String s) {
        return getEnabledMonitorConfig();

    }

    @Override
    public MonitorConfiguration getDefaultConfiguration() {
        return getDisabledMonitorConfig();

    }

    private MonitorConfiguration getEnabledMonitorConfig() {
        return new MonitorConfiguration() {
            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public long getThreshold() {
                return 1000L;
            }
        };
    }

    private MonitorConfiguration getDisabledMonitorConfig() {
        return new MonitorConfiguration() {
            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public long getThreshold() {
                return 0L;
            }
        };
    }
}
