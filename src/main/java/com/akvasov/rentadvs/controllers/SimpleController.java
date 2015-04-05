package com.akvasov.rentadvs.controllers;

import com.akvasov.rentadvs.backend.core.Core;
import com.akvasov.rentadvs.db.DAO.AdvDAO;
import com.akvasov.rentadvs.db.DAO.FriendsDAO;
import com.akvasov.rentadvs.db.DAO.MongoImpl.AdvDAOMongoImpl;
import com.akvasov.rentadvs.db.DAO.MongoImpl.FriendsDAOMongoImpl;
import com.akvasov.rentadvs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 05.04.15.
 */
@Controller
@Scope(value ="singleton")
@RequestMapping("/simple")
public class SimpleController {

    @Autowired
    FriendsDAO friendsDAO;

    @Autowired
    AdvDAO advDAO;

    @Autowired
    Core core;

    @RequestMapping(value = "/add_yana", method = RequestMethod.GET)
    public String addYana(ModelMap model) {
        friendsDAO.clear();
        advDAO.clear();

        List<User> initiateUsers = new ArrayList();
        initiateUsers.add(new User("47735", "Яна"));
        friendsDAO.addUsers(initiateUsers);

        Boolean started = core.start();

        if (started){
            model.addAttribute("message", "Yana added successfully");
        } else {
            model.addAttribute("message", "Yana added with errors:(");
        }

        return "hello";
    }

    @RequestMapping(value = "/yana_friends_count", method = RequestMethod.GET)
    public String printYana(ModelMap model) {
        List<User> users = core.getUsers();
        model.addAttribute("message", "Yana has " + users.size() + " friends");
        return "hello";
    }
}
