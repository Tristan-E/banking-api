package com.tulover;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author teyma
 * @since 21/05/18
 */
public class TransactionIT {

    Server jettyServer;

    @Before
    public void setUp() throws Exception {
        jettyServer = com.tulover.ITServerConfig.intializeITServer();
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
    public void test() throws IOException {

        // Given
        HttpUriRequest request = new HttpGet("http://localhost:8080/api/transactions");

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(),HttpStatus.SC_OK);
    }
}