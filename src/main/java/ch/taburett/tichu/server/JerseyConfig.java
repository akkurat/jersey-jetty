package ch.taburett.tichu.server;

import ch.taburett.tichu.resources.GamesResource;
import ch.taburett.tichu.resources.HelloResource;
import ch.taburett.tichu.resources.RoomsResource;
import ch.taburett.tichu.services.GameService;
import ch.taburett.tichu.services.ProxyPlayerService;
import ch.taburett.tichu.services.RoomService;
import ch.taburett.tichu.sockets.PlayerSocketServlet;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

import static org.glassfish.hk2.utilities.ServiceLocatorUtilities.bind;

// 'services', '/services', or '/services/*'
// is all the same. Jersey will change it to be '/services/*'
@ApplicationPath("/api/")
@OpenAPIDefinition(
        tags = {
                @Tag(name = RoomsResource.ROOM, description = "Room administration"),
                @Tag(name = GamesResource.GAME, description = "Game administration"),
                @Tag(name = HelloResource.GREETING, description = "Administration of greetins")
        },
        info = @Info(
                title = "Gagi",
                version = "1.0.0"

        ),
        servers = {
                @Server(url = "http://localhost:8080",
                        description = "Rooms")
        }

)
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        register(new SingeltonBinder<>(RoomService.class));
        register(new SingeltonBinder<>(GameService.class));

        packages("ch.taburett.tichu.resources");
        register(OpenApiResource.class);
//        register(ProxyPlayerService.class);
//        register(PlayerSocketServlet.class);
    }

    private static  class SingeltonBinder<T> extends AbstractBinder {

        private Class<T> serviceType;

        public SingeltonBinder(Class<T> serviceType) {
            this.serviceType = serviceType;
        }

        @Override
        protected void configure() {
            bind(serviceType)
                    .to(serviceType).in(Singleton.class);
        }
    }
}