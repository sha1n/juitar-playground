package juitar.web.rest.resource;

import org.juitar.flags.*;

import javax.ws.rs.*;
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

    @GET
    @Path("/feature")
    @Produces(MediaType.TEXT_PLAIN)
    public Response feature(@QueryParam("flag") String flagName,
                            @QueryParam("scope")
                            @DefaultValue("null")
                            String scopeName) {
        Flag flag = Flags.get(flagName);
        Scope scope = Scopes.get(scopeName);
        MyFeature instance = new FeatureFactory(flag).getInstance("", scope);
        return Response.ok().entity(instance.getValue()).build();
    }


    private static class FeatureFactory extends AbstractFlaggedFeatureFactory<MyFeature, String> {

        protected FeatureFactory(Flag flag) {
            super(flag);
        }

        @Override
        protected MyFeature getFeatureFlagOn(String arg) {
            return new MyFeature() {
                @Override
                public String getValue() {
                    return "Flag is on!";
                }
            };
        }

        @Override
        protected MyFeature getFeatureFlagOff(String arg) {
            return new MyFeature() {
                @Override
                public String getValue() {
                    return "Flag is off!";
                }
            };
        }
    }
}
