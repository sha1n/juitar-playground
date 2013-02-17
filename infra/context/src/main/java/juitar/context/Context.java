package juitar.context;

import org.slf4j.Logger;

/**
 * @author sha1n
 * Date: 1/4/13
 */
public interface Context {

    UserContext getCurrentUser();

    Logger getContextLogger();
}
