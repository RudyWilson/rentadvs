package com.akvasov.rentadvs.config;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.Sources;

/**
 * Created by user on 27.08.14.
 */

@Sources({
    "classpath:config/server.properties"
})
public interface ServerConfig extends Config {
    //Database properties
    @Key("database.type")
    @DefaultValue("mongo")
    String databaseType();

    @Key("database.host")
    @DefaultValue("127.0.0.1")
    String databaseHost();

    @Key("database.port")
    @DefaultValue("27017")
    int databasePort();

    @Key("database.name")
    @DefaultValue("ellebliss")
    String databaseName();

    //Scheduler properties
    @Key("scheduler.period")
    @DefaultValue("100")
    Long schedulerPeriod();

    @Key("scheduler.sleepAfterWork")
    @DefaultValue("5000")
    Long schedulerSleepAfterWork();

}
