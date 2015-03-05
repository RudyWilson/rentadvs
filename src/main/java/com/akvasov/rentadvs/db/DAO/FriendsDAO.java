package com.akvasov.rentadvs.db.DAO;

import com.akvasov.rentadvs.model.User;

import java.util.List;

/**
 * Created by akvasov on 17.07.14.
 */
public interface FriendsDAO {

    public List<User> loadAllUsers();

    void deleteUsers(List<User> users);

    void addUsers(List<User> users);

    void clear();
}
