package com.akvasov.rentadvs.backend.command;

import com.akvasov.rentadvs.backend.core.Worker;
import com.akvasov.rentadvs.backend.controller.PageController;
import com.akvasov.rentadvs.model.User;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 17.07.14.
 */
public class GetFriendsCommand implements Command {

    private static Logger LOGGER = Logger.getLogger(GetFriendsCommand.class.getName());

    private final PageController pageController;
    private final User user;

    private final Worker worker;

    public GetFriendsCommand(Worker worker, PageController pageController, User user) {
        this.worker = worker;
        this.pageController = pageController;
        this.user = user;
    }

    @Override
    public Boolean call() throws Exception {
        LOGGER.fine("GetFriendsCommand call");
        List<User> friends = null;
        try {
            friends = pageController.getFriends(user.getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        worker.addUsers(friends);
        worker.setFriends(user, friends);
        return true;
    }

}
