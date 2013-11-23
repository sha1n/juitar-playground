package juitar.web.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author sha1n
 *         Date: 11/23/13
 */
@Path("/status")
public class Status {

    @GET
    public Response status() {
        return Response.ok().entity("I'm OK").build();
    }
}
