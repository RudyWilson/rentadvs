package com.akvasov.rentadvs;

import com.akvasov.rentadvs.backend.controller.PageControllerHttpImpl;
import com.akvasov.rentadvs.backend.controller.PageControllerTestStaticImpl;
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
 * Created by alex on 21.06.14.
 */
public class TestMainStatic {

    public static final Logger logger = Logger.getLogger(TestMainStatic.class.getName());

    public static void main(String[] args) throws Exception {
        LogManager.getLogManager().readConfiguration(TestMainStatic.class.getResourceAsStream("/config/logging.properties"));
        logger.log(Level.FINE, "Start apps");

        List<User> result = new ArrayList();
        User initialUser = new User();
        initialUser.setId("0");
        initialUser.setName("user 0");
        result.add(initialUser);

        FriendsDAOMongoImpl friendsDAOMongo = new FriendsDAOMongoImpl();
        friendsDAOMongo.clear();
        friendsDAOMongo.addUsers(result);

        AdvDAOMongoImpl advDAOMongo = new AdvDAOMongoImpl();
        advDAOMongo.clear();

        Core core = new Core(new PageControllerTestStaticImpl(), friendsDAOMongo, advDAOMongo);
        core.start();

    }
}
