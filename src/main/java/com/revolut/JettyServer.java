package com.revolut;

import com.revolut.controller.EntryPoint;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.DefaultJaxrsConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

/**
 * @author teyma
 * @since 18/05/2018
 */
public class JettyServer {

    static final String APPLICATION_PATH = "/api";
    static final String CONTEXT_ROOT = "/";

    public static void main(String[] args) throws Exception {
        Server jettyServer = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(CONTEXT_ROOT);
        context.addEventListener(new Listener());
        jettyServer.setHandler(context);

        // Setup API resources
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, APPLICATION_PATH + "/*");
        jerseyServlet.setInitOrder(1);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.revolut.controller;io.swagger.jaxrs.json;io.swagger.jaxrs.listing"
        );

        // Setup Swagger servlet
        ServletHolder swaggerServlet = context.addServlet(DefaultJaxrsConfig.class, "/swagger-core");
        swaggerServlet.setInitOrder(2);
        swaggerServlet.setInitParameter("api.version", "1.0.0");
        swaggerServlet.setInitParameter("swagger.api.basepath", APPLICATION_PATH);


        // Setup Swagger-UI static resources
        String resourceBasePath = JettyServer.class.getResource("/swagger-ui").toExternalForm();
        context.setWelcomeFiles(new String[] {"index.html"});
        context.setResourceBase(resourceBasePath);
        context.addServlet(new ServletHolder(new DefaultServlet()), "/*");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}
