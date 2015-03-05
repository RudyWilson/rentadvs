package com.akvasov.rentadvs.db.controller;

import com.akvasov.rentadvs.db.DAO.FriendsDAO;

import java.util.List;

/**
 * Created by akvasov on 17.07.14.
 */
public class FriendsController {

    FriendsDAO friendsDAO = null;

    private FriendsController() {
    }

    public FriendsController(FriendsDAO friendsDAO){
        this.friendsDAO = friendsDAO;
    }

    public List loadAllUsers(){
        return friendsDAO.loadAllUsers();
    }

    public void setFriendsDAO(FriendsDAO friendsDAO) {
        this.friendsDAO = friendsDAO;
    }
}
