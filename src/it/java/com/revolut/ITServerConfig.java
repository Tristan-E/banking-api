package com.revolut;

import com.revolut.ApplicationProperties;
import org.aeonbits.owner.ConfigFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Server for IT.
 * Same as the one running the app minus uncessary settings (H2 console, swagger)
 * @author teyma
 * @since 28/05/18
 */
public final class ITServerConfig {

    public static Server intializeITServer() {
        // Loading application properties
        ApplicationProperties props = ConfigFactory.create(ApplicationProperties.class);

        final String CONTEXT_ROOT = "/";
        final String APPLICATION_PATH = "/api";

        Server jettyServer = new Server(props.applicationPort());
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(CONTEXT_ROOT);
        jettyServer.setHandler(context);

        // Setup API resources
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, APPLICATION_PATH + "/*");
        jerseyServlet.setInitOrder(1);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.revolut.controller"
        );

        // Dependency Injection with binders
        jerseyServlet.setInitParameter("javax.ws.rs.Application", "com.revolut.ApplicationConfig");

        return jettyServer;
    }

}
