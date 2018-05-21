package com.revolut;

import com.revolut.persistence.PersistenceUtil;
import com.revolut.model.Transaction;
import io.swagger.jaxrs.config.DefaultJaxrsConfig;
import org.aeonbits.owner.ConfigFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

/**
 * @author teyma
 * @since 18/05/2018
 */
public class JettyServer {

    static final String APPLICATION_PATH = "/api";
    static final String CONTEXT_ROOT = "/";

    public static void main(String[] args) throws Exception {

        // Loading application properties
        ApplicationProperties props = ConfigFactory.create(ApplicationProperties.class);

        // Access to the h2 console if enabled
        if(props.isH2ConsoleEnabled()) {
            org.h2.tools.Server.createWebServer(new String[]{"-web","-webAllowOthers","-webPort", props.h2ConsolePort()}).start();
        }

        Server jettyServer = new Server(props.applicationPort());
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(CONTEXT_ROOT);
        jettyServer.setHandler(context);

        // Setup API resources
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, APPLICATION_PATH + "/*");
        jerseyServlet.setInitOrder(1);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.revolut.controller;io.swagger.jaxrs.json;io.swagger.jaxrs.listing"
        );

        // Dependency Injection with binders
        jerseyServlet.setInitParameter("javax.ws.rs.Application", "com.revolut.ApplicationConfig");

        // Setup Swagger servlet
        ServletHolder swaggerServlet = context.addServlet(DefaultJaxrsConfig.class, "/swagger-core");
        swaggerServlet.setInitOrder(2);
        swaggerServlet.setInitParameter("api.version", props.apiVersion());
        swaggerServlet.setInitParameter("swagger.api.basepath", APPLICATION_PATH);

        // Setup Swagger-UI static resources
        String resourceBasePath = JettyServer.class.getResource("/swagger-ui").toExternalForm();
        context.setWelcomeFiles(new String[] {"index.html"});
        context.setResourceBase(resourceBasePath);
        context.addServlet(new ServletHolder(new DefaultServlet()), "/*");

        // TODO REMOVE
        generateInitialContent();

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    // TODO REMOVE
    private static void generateInitialContent() {
        EntityManager entityManager = PersistenceUtil.getEntityManager();

        // before persisting an object to db, starting the transaction
        entityManager.getTransaction().begin();

        // first entity
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(1010));

        // persisting it to database
        entityManager.persist(transaction);

        entityManager.getTransaction().commit();
    }
}
