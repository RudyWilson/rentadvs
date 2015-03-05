package com.akvasov.rentadvs.db.DAO.MongoImpl;

import com.akvasov.rentadvs.config.ServerConfig;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.aeonbits.owner.ConfigFactory;

import java.net.UnknownHostException;

/**
 * Created by alex on 25.08.14.
 */
public abstract class MongoCommonDAO {

    private static ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    private static String databaseName = cfg.databaseName();
    protected String collectionName = getCollection();

    private String host = cfg.databaseHost();
    private Integer port = cfg.databasePort();

    private MongoClient mongoClient;
    private DB db;
    protected DBCollection collection;

    public MongoCommonDAO() {
        try {
            connect();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void connect() throws UnknownHostException {
        mongoClient = new MongoClient(host, port);
        db = mongoClient.getDB(databaseName);
        collection = db.getCollection(collectionName);
    }

    abstract protected String getCollection();

}
