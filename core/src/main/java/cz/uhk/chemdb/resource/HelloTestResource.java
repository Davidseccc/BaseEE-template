package cz.uhk.chemdb.resource;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Stateless
@Path("/test")
public class HelloTestResource {
    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getAllUsers(@Context SecurityContext securityContext) {
        JsonObject out = Json.createObjectBuilder().add("msg", "Hello world").build();
        return Response.ok(out).build();
    }
}