package juitar.web.rest.resource;

import juitar.monitoring.spi.config.MonitoredCategory;
import junitar.server.netty.jersey.CommonQuery;
import junitar.server.netty.jersey.Param;
import junitar.server.netty.jersey.ResourceQuery;
import org.juitar.monitoring.api.Monitored;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author sha1n
 * Date: 1/22/13
 */
@Path("/injectable")
public class InjectableTest {

    @CommonQuery(required = {Param.LAYOUT, Param.FILTER})
    ResourceQuery query;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Monitored(threshold = 3, category = MonitoredCategory.REST)
    public String get() {
        return query.toString();
    }

}
