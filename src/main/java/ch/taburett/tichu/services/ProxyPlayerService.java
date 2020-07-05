package ch.taburett.tichu.services;

import ch.taburett.tichu.models.ProxyPlayer;
import com.google.common.collect.ImmutableList;
import org.jvnet.hk2.annotations.Service;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


@Service
public class ProxyPlayerService {
    ConcurrentHashMap<UUID, ProxyPlayer> players = new ConcurrentHashMap<>();
    ConcurrentHashMap<Session, ProxyPlayer> establishedSessions = new ConcurrentHashMap<>();
    public ProxyPlayerService() {
    }

    public ProxyPlayer get( String uuid )
    {
        UUID uuid1 = UUID.fromString( uuid );
        ProxyPlayer proxyPlayer = players.get(uuid1);
        return proxyPlayer;
    }

    public List<ProxyPlayer> get()
    {
        return ImmutableList.copyOf( players.values() );
    }


    public CompletableFuture<String> closeConnection (String id) {
        UUID uuid = UUID.fromString( id );
        ProxyPlayer player = players.get(uuid);
        return player.closeConnection()
                .thenApply( (s) -> {players.remove(uuid); return s; });
    }

    public void addSession(Session session) throws IOException {

        session.getBasicRemote().sendText("authenticate");

        session.addMessageHandler(new MyMessageHandler(s -> establishedSessions.put(session, new ProxyPlayer())));

//        session.addMessageHandler(String.class, new MessageHandler.Whole<String>() {
//            @Override
//            public void onMessage(String m) {
//                System.out.print(m);
//                establishedSessions.put(session, new ProxyPlayer());
//            }
//        });

//        CompletableFuture.delayedExecutor(10, TimeUnit.SECONDS).execute(
//                () ->  {
//                    if (!establishedSessions.contains(session)) {
//                        try {
//                            session.close( new CloseReason(new CloseReason.CloseCode() {
//                                @Override
//                                public int getCode() {
//                                    return 2000;
//                                }
//                            }, "Player authentication Timed out"));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//        );
    }


    public void unregisterSession(Session session) {
    }

    private static class MyMessageHandler implements MessageHandler.Whole<String> {

        private final Consumer<String> action;

        public MyMessageHandler(Consumer<String> action) {
            this.action = action;
        }

        @Override
        public void onMessage(String message) {
            System.out.println("ha " + message);

        }
    }


}
