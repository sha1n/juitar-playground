package juitar.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author sha1n
 * Date: 1/4/13
 */
final class ContextImpl implements Context {

    private static final Logger CONTEXT_LOGGER = LoggerFactory.getLogger("CONTEXT_LOGGER");
    private UserContext userContext = new UserContextImpl();
    private final HashMap<String, Object> parameters = new HashMap<String, Object>();

    public <T> T getParameter(String key) {
        return (T) parameters.get(key);
    }

    public boolean isParemeterExist(String key) {
        return parameters.containsKey(key);
    }

    @Override
    public UserContext getCurrentUser() {
        return userContext;
    }

    @Override
    public Logger getContextLogger() {
        return CONTEXT_LOGGER;
    }

}
