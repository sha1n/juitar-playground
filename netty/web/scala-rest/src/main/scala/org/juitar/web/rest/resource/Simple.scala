package org.juitar.web.rest.resource

import javax.ws.rs._
import javax.ws.rs.core.{MediaType, Response}

/**
 * Created by sha1n on 5/28/14.
 */
@Path("/scala/status")
class Simple {

  @GET
  @Produces(Array.apply(MediaType.TEXT_PLAIN))
  def get() = {
    Response.ok("I'm a JAX-RS resource, implemented in Scala").build()
  }

  @POST
  @Produces(Array.apply(MediaType.TEXT_PLAIN))
  @Consumes(Array.apply(MediaType.TEXT_PLAIN))
  def post(entity: String) = {
    // Just echo the entity back... who cares...
    Response.ok(entity).build()
  }

}
