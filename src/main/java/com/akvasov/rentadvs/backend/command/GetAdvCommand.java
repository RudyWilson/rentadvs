package com.akvasov.rentadvs.backend.command;

import com.akvasov.rentadvs.backend.core.Worker;
import com.akvasov.rentadvs.backend.controller.PageController;
import com.akvasov.rentadvs.model.Advertsment;
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
public class GetAdvCommand implements Command {

    private static Logger LOGGER = Logger.getLogger(GetAdvCommand.class.getName());

    @Autowired
    private PageController pageController;

    private final User user;
    private final Worker worker;

    public GetAdvCommand(Worker worker, User user) {
        this.user = user;
        this.worker = worker;
    }

    @Override
    public Boolean call() throws Exception {
        LOGGER.fine("GetAdvCommand Call");
        List<Advertsment> advertsments = null;

        try {
            advertsments = pageController.getAdvertsments(user.getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        worker.addAdvs(advertsments);
        return true;
    }

}
