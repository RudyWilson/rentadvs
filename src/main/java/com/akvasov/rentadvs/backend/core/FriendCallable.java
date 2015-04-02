package com.akvasov.rentadvs.backend.core;

import com.akvasov.rentadvs.backend.controller.PageController;
import com.akvasov.rentadvs.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by akvasov on 17.07.14.
 */
public class FriendCallable implements Callable<List<User>> {

    @Autowired
    private PageController pageController;
    private final User user;

    public FriendCallable(User user) {
        this.user = user;
    }

    @Override
    public List<User> call() throws Exception {
        List<User> friends = pageController.getFriends(user.getId().toString());
        return friends;
    }
}
