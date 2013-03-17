package junitar.server.netty.jersey;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.PerRequestTypeInjectableProvider;

import javax.ws.rs.ext.Provider;

/**
 * @author sha1n
 * Date: 3/17/13
 */
@Provider
public class TestInjectableProvider extends PerRequestTypeInjectableProvider<Query, ResourceQuery> {


    /**
     * Construct a new instance with the Type
     */
    public TestInjectableProvider() {
        super(ResourceQuery.class);
    }

    @Override
    public Injectable<ResourceQuery> getInjectable(ComponentContext ic, Query query) {
        return new QueryInjectable(query);
    }
}
