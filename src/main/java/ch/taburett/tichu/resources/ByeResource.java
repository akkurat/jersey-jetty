package ch.taburett.tichu.resources;


import ch.taburett.tichu.models.Greeting;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/bye")
@Tag( name = HelloResource.GREETING)
public class ByeResource {

    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting hello(@PathParam("param") String name) {
        return new Greeting(name);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String helloUsingJson(Greeting greeting) {
        return greeting.getMessage() + "\n";
    }
}