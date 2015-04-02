package com.akvasov.rentadvs.backend.core;

import com.akvasov.rentadvs.backend.controller.PageController;
import com.akvasov.rentadvs.model.User;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by akvasov on 17.07.14.
 */
public class FriendCallable implements Callable<List<User>> {

    private final PageController pageController;
    private final User user;

    public FriendCallable(PageController pageController, User user) {
        this.pageController = pageController;
        this.user = user;
    }

    @Override
    public List<User> call() throws Exception {
        List<User> friends = pageController.getFriends(user.getId().toString());
        return friends;
    }
}
