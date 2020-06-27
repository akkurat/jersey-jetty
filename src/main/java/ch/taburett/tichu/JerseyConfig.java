package ch.taburett.tichu;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

// 'services', '/services', or '/services/*'
// is all the same. Jersey will change it to be '/services/*'
@ApplicationPath("/api/")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("ch.taburett.tichu.resources");
        register(OpenApiResource.class);
    }
}