package ch.taburett.tichu;

import ch.taburett.tichu.server.JettyServer;
import ch.taburett.tichu.server.WebsocketModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunServer {

    private static Logger LOGGER = LoggerFactory.getLogger(RunServer.class);

    public static void main(String[] args) throws Exception {
        Integer port = getPort();

        LOGGER.info("Starting jetty on port: {}", port);

        JettyServer server = new JettyServer(port, createInjector());
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        server.start();
    }

    private static Integer getPort() {
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "8080";
        }

        return Integer.valueOf(port);
    }

    private static Injector createInjector() {
        return Guice.createInjector(new WebsocketModule());
//        return LifecycleInjector.builder()
//                .withModules(new WebsocketModule())
//                .build()
//                .createInjector();
    }

}