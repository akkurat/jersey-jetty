package ch.taburett.tichu.resources;

import ch.taburett.tichu.models.Game;
import ch.taburett.tichu.models.Room;
import ch.taburett.tichu.services.GameService;
import ch.taburett.tichu.services.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/game")
@Tag(name = GamesResource.GAME)
public class GamesResource {

    public static final String GAME = "game";
    @Inject
    GameService gameService;

    @Inject
    RoomService roomService;



    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Game createGame( String roomId )
    {
        Room room = roomService.get(UUID.fromString(roomId));
        return gameService.start( room );
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Game> getRooms() {
        return gameService.get();
    }

    @DELETE
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game delete(@PathParam("uuid") String roomId ) {
        Room room = roomService.get(UUID.fromString(roomId));
        return gameService.delete(room);
    }
}


