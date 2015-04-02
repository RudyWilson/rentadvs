package sanityCheck;

import com.akvasov.rentadvs.backend.controller.PageControllerTestStaticImpl;
import com.akvasov.rentadvs.backend.core.Core;
import com.akvasov.rentadvs.db.DAO.AdvDAO;
import com.akvasov.rentadvs.db.DAO.FriendsDAO;
import com.akvasov.rentadvs.db.DAO.MongoImpl.AdvDAOMongoImpl;
import com.akvasov.rentadvs.db.DAO.MongoImpl.FriendsDAOMongoImpl;
import com.akvasov.rentadvs.model.Advertsment;
import com.akvasov.rentadvs.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class SanityCheckWithStaticDataTest {

    @Autowired
    AdvDAO advDAOMongo;

    @Autowired
    FriendsDAO friendsDAO;

    @Autowired
    Core core;

    @Before
    public void cleanDao(){
        friendsDAO.clear();
        advDAOMongo.clear();
    }

    @Test
    public void testMain() throws Exception {
        List<User> result = new ArrayList();
        User initialUser = new User();
        initialUser.setId("0");
        initialUser.setName("user 0");
        result.add(initialUser);

        friendsDAO.addUsers(result);

        core.start();
        core.waitUntilRunning();

        List<User> users = core.getUsers();
        List<Advertsment> advs = core.getAdvs();

        assertEquals(users.size(), 62);
        assertEquals(advs.size(), 26);
    }
}