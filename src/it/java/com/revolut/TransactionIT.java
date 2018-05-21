package com.revolut;

import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.*;

import java.io.IOException;

/**
 * @author teyma
 * @since 21/05/18
 */
public class TransactionIT {

    /**
     * TODO
     * NOT THE RIGHT WAY TO START THE SERVER FOR INTEGRATION TEST
     * TRY TO USE
     *   <groupId>org.eclipse.jetty</groupId>
     *   <artifactId>jetty-maven-plugin</artifactId>
     * AND SWITCH TO A WEB XML CONFIGURATION
     */

    Server jettyServer;

    @Before
    public void setUp() throws Exception {
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

        jettyServer.start();
    }

    @After
    public void tearDown() throws Exception {
        if (jettyServer != null) {
            jettyServer.stop();
            jettyServer.join();
            jettyServer.destroy();
            jettyServer = null;
        }
    }

    @Test
    public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived() throws IOException {

        // Given
        HttpUriRequest request = new HttpGet("http://localhost:8080/api/transactions");

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(),HttpStatus.SC_OK);
    }
}