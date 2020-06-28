package ch.taburett.tichu;

import ch.taburett.tichu.resources.HelloResource;
import ch.taburett.tichu.resources.RoomsResource;
import ch.taburett.tichu.services.RoomService;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
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

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(RoomService.class)
                        .to(RoomService.class).in(Singleton.class);
            }
        });

        packages("ch.taburett.tichu.resources");
        register(OpenApiResource.class);
    }
}