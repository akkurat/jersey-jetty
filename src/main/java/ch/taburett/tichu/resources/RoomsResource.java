package ch.taburett.tichu.resources;

import ch.taburett.tichu.models.Room;
import ch.taburett.tichu.services.RoomService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/room")
@Tag(name = RoomsResource.ROOM)
public class RoomsResource {

    public static final String ROOM = "room";
    @Inject
    RoomService roomService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Room createRoom( Room room )
    {

        return roomService.create( room.getCaption(), room.getPlayers() );
    }

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public boolean add(Room room )
//    {
//        return roomService.add( room );
//    }
//
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getRooms() {
        return roomService.get();
    }

    @DELETE
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room delete( @PathParam("uuid") String uuid ) {
        return roomService.delete(uuid);
    }
}


