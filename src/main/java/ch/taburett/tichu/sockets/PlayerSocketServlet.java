package ch.taburett.tichu.sockets;


import ch.taburett.tichu.services.JwtService;
import ch.taburett.tichu.services.ProxyPlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;


@Singleton
@ServerEndpoint(value = "/websockets/players"
)
public class PlayerSocketServlet {

    @Inject
    ProxyPlayerService playerService;

    @Inject
    JwtService jwtService;

    public PlayerSocketServlet() {
//        playerService = shit;
        System.out.println("I was constructed");
    }


    @OnOpen
    public void onOpen( Session session ) throws IOException {
        playerService.addSession( session );
    }

    @OnMessage
    public void consumeMessage( String message, Session session ) throws JsonProcessingException {
        Map map = new ObjectMapper().readValue(message, Map.class);

        if(map.containsKey("jwt"))
        {
            String token = (String) map.get("jwt");
            jwtService.verify(token);
            System.out.println(token + " auth");
        }

        System.out.println(message);
    }

    @OnClose
    public void close(Session session) {
        playerService.unregisterSession(session);
    }
}
