package com.tulover;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author teyma
 * @since 20/05/2018
 */
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages("com.tulover.controller");
        register(new ApplicationBinder());
    }
}
