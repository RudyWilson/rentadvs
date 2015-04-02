package com.akvasov.rentadvs.main;

import com.akvasov.rentadvs.backend.controller.PageControllerHttpImpl;
import com.akvasov.rentadvs.backend.core.Core;
import com.akvasov.rentadvs.db.DAO.MongoImpl.AdvDAOMongoImpl;
import com.akvasov.rentadvs.db.DAO.MongoImpl.FriendsDAOMongoImpl;
import com.akvasov.rentadvs.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by alex on 13.03.15.
 */
public class Main {

    public static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/config/logging.properties"));
        logger.log(Level.FINE, "Start apps");

        FriendsDAOMongoImpl friendsDAOMongo = new FriendsDAOMongoImpl();
        List<User> initiateUsers = new ArrayList();
        initiateUsers.add(new User("47735","Яна"));
        friendsDAOMongo.clear();
        friendsDAOMongo.addUsers(initiateUsers);

        AdvDAOMongoImpl advDAOMongo = new AdvDAOMongoImpl();
        advDAOMongo.clear();

        Core core = new Core();
        core.start();
    }

}
