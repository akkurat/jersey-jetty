package ch.taburett.tichu;

import ch.taburett.tichu.services.RoomService;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.DispatcherType;

import java.util.EnumSet;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class JettyJerseyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyJerseyServer.class);


    public static void main(String[] args) {

        Server server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler(NO_SESSIONS);

        addCorsFilter(servletContextHandler);

        servletContextHandler.setContextPath("/");
        server.setHandler(servletContextHandler);

        servletContextHandler.addServlet(new ServletHolder( new ServletContainer( new JerseyConfig()) ), "/api/*");


        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            logger.error("Error occurred while starting Jetty", ex);
            System.exit(1);
        }

        finally {
            server.destroy();
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
}
