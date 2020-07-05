package ch.taburett.tichu.server;

import ch.taburett.tichu.sockets.GuiceConfigurator;
import ch.taburett.tichu.sockets.PlayerSocketServlet;
import com.google.inject.Injector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.websocket.api.InvalidWebSocketException;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class JettyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);
    private final Integer port;
    private final Injector injector;
    private final GuiceConfigurator configurator;
    private Server server;

    public JettyServer(Integer port, Injector injector) {
        this.port = port;
        this.injector = injector;
        this.configurator = new GuiceConfigurator(injector);
    }


    public void start() throws IOException, DeploymentException, ServletException {

        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(NO_SESSIONS);
        context.setServer( server );


        context.setContextPath("/");

        context.setBaseResource(Resource.newResource(JettyServer.class.getResource("/webapp")));
        context.addServlet(DefaultServlet.class,"/");
        context.setWelcomeFiles(new String[]{"test.html"});

        addCorsFilter(context);
//        context.addFilter(GuiceFilter.class, "/*",
//                EnumSet.of(javax.servlet.DispatcherType.REQUEST,
//                        javax.servlet.DispatcherType.ASYNC));
//
//        context.addEventListener(new GuiceListener());

        server.setHandler(context);

        context.addServlet(new ServletHolder( new ServletContainer( new JerseyConfig()) ), "/api/*");

        // https://github.com/jetty-project/embedded-jetty-websocket-examples/blob/master/javax.websocket-example/src/main/java/org/eclipse/jetty/demo/EventServer.java
        ServerContainer wscontainer = WebSocketServerContainerInitializer.initialize(context);
        wscontainer.addEndpoint( createEndpointConfig(PlayerSocketServlet.class) );








        try {
            server.start();
        } catch (Exception ex) {
            logger.error("Error occurred while starting Jetty", ex);
            System.exit(1);
        }

    }

    public boolean shutdown() {
        try {
            server.stop();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void addCorsFilter(ServletContextHandler servletContextHandler) {
        FilterHolder cors = servletContextHandler.addFilter(CrossOriginFilter.class,
                "/api/*", EnumSet.of(DispatcherType.REQUEST));
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD,PUT,DELETE,OPTIONS");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
    }

    private ServerEndpointConfig createEndpointConfig(Class<?> endpointClass) throws DeploymentException {
        ServerEndpoint annotation = endpointClass.getAnnotation(ServerEndpoint.class);
        if (annotation == null) {
            throw new InvalidWebSocketException("Unsupported WebSocket object, missing @" + ServerEndpoint.class + " annotation");
        }

        return ServerEndpointConfig.Builder.create(endpointClass, annotation.value())
                .subprotocols(Arrays.asList(annotation.subprotocols()))
                .decoders(Arrays.asList(annotation.decoders()))
                .encoders(Arrays.asList(annotation.encoders()))
                .configurator(configurator)
                .build();
    }
}
