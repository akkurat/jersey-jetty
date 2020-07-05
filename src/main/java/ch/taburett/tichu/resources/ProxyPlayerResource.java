package ch.taburett.tichu.resources;

import ch.taburett.tichu.models.ProxyPlayer;
import com.google.common.collect.ImmutableList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/player/")
public class ProxyPlayerResource {

    @GET
    public List<ProxyPlayer> getActivePlayers( )
    {
        return ImmutableList.of();
    }

}
