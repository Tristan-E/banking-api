package com.tulover;

import org.aeonbits.owner.Config;

/**
 * @author teyma
 * @since 21/05/18
 */
@Config.Sources("classpath:application.properties")
public interface ApplicationProperties extends Config {

    @Key("app.port")
    @DefaultValue("8080")
    int applicationPort();

    @Key("app.api.version")
    String apiVersion();

    @Key("h2.console.enabled")
    @DefaultValue("false")
    boolean isH2ConsoleEnabled();

    @Key("h2.console.port")
    @DefaultValue("7071")
    String h2ConsolePort();
}
