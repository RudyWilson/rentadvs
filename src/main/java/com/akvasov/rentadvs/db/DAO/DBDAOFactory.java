package com.akvasov.rentadvs.db.DAO;

import com.akvasov.rentadvs.config.ServerConfig;
import com.akvasov.rentadvs.db.DAO.MongoImpl.AdvDAOMongoImpl;
import com.akvasov.rentadvs.db.DAO.MongoImpl.FriendsDAOMongoImpl;
import org.aeonbits.owner.ConfigFactory;

/**
 * Created by user on 27.08.14.
 */
public final class DBDAOFactory {

    private static FriendsDAO friendsDAO;
    private static AdvDAO advDAO;
    private static String databaseType = "";

    private static ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    private static Boolean isDatabaseTypeUpdate() {
        String newDatabaseType = cfg.databaseType();
        if (!databaseType.equals(newDatabaseType)) {
            databaseType = newDatabaseType;
            return true;
        }
        return false;
    }

    public static FriendsDAO getFriendsDAO() {
        if (isDatabaseTypeUpdate()) {
            if (databaseType.equals("mongo")) {
                friendsDAO = new FriendsDAOMongoImpl();
            } else {
                throw new RuntimeException("Database type " + databaseType + " does not known");
            }
        }
        return friendsDAO;
    }

    public static AdvDAO getAdvDAO() {
        if (isDatabaseTypeUpdate()) {
            if (databaseType.equals("mongo")) {
                advDAO = new AdvDAOMongoImpl();
            } else {
                throw new RuntimeException("Database type " + databaseType + " does not known");
            }
        }
        return advDAO;
    }

}
