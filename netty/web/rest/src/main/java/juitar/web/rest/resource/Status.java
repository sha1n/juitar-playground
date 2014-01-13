package juitar.web.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author sha1n
 *         Date: 11/23/13
 */
@Path("/status")
public class Status {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response status() {
        return Response.ok().entity("I'm OK, I really am..." +
                "This is a plain text response string, I hope it's long enough to " +
                "be broken into many many many chunks, so I can debug my stupid client...").build();
    }

}
