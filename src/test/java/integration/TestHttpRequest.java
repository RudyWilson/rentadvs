package integration;

import com.akvasov.rentadvs.backend.controller.PageControllerHttpImpl;
import com.akvasov.rentadvs.model.User;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by alex on 12.03.15.
 */
public class TestHttpRequest {

    public static final Logger logger = Logger.getLogger(TestHttpRequest.class.getName());

    public static void main(String[] args) throws Exception {
        LogManager.getLogManager().readConfiguration(TestHttpRequest.class.getResourceAsStream("/config/logging.properties"));
        logger.log(Level.FINE, "Start apps");

        PageControllerHttpImpl pageControllerHttp = new PageControllerHttpImpl();
        List<User> yana_is = pageControllerHttp.getFriends("47735");
        System.out.println("yana_is = " + yana_is);
    }

}
