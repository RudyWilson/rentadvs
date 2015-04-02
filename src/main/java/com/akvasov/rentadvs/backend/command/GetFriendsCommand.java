package com.akvasov.rentadvs.backend.command;

import com.akvasov.rentadvs.backend.core.Worker;
import com.akvasov.rentadvs.backend.controller.PageController;
import com.akvasov.rentadvs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 17.07.14.
 */
@Component
@Scope(value = "prototype")
public class GetFriendsCommand implements Command {

    private static Logger LOGGER = Logger.getLogger(GetFriendsCommand.class.getName());

    @Autowired
    private PageController pageController;

    private final User user;

    private final Worker worker;

    public GetFriendsCommand(Worker worker, User user) {
        this.worker = worker;
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
