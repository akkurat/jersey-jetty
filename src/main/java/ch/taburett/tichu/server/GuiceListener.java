package ch.taburett.tichu.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
//                new ConfigurationModule(),
//                new SwaggerModule(getClass().getPackage().getImplementationVersion()),
//                new JerseyConfig(),
//                new ValidationModule(),
                new WebsocketModule()
        );
    }
}
