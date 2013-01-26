package juitar.context;

import org.slf4j.Logger;

/**
 * @author sha1n
 * Date: 1/4/13
 */
public interface Context extends Correlated, org.juitar.monitoring.spi.Context {

    UserContext getCurrentUser();

    Logger getContextLogger();
}
