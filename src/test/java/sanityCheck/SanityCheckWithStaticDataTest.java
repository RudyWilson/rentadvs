package sanityCheck;

import com.akvasov.rentadvs.backend.controller.PageControllerTestStaticImpl;
import com.akvasov.rentadvs.backend.core.Core;
import com.akvasov.rentadvs.db.DAO.MongoImpl.AdvDAOMongoImpl;
import com.akvasov.rentadvs.db.DAO.MongoImpl.FriendsDAOMongoImpl;
import com.akvasov.rentadvs.model.Advertsment;
import com.akvasov.rentadvs.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SanityCheckWithStaticDataTest {

    @Test
    public void testMain() throws Exception {
        List<User> result = new ArrayList();
        User initialUser = new User();
        initialUser.setId("0");
        initialUser.setName("user 0");
        result.add(initialUser);

        FriendsDAOMongoImpl friendsDAOMongo = new FriendsDAOMongoImpl();
        friendsDAOMongo.clear();
        friendsDAOMongo.addUsers(result);

        AdvDAOMongoImpl advDAOMongo = new AdvDAOMongoImpl();
        advDAOMongo.clear();

        Core core = new Core(new PageControllerTestStaticImpl(), friendsDAOMongo, advDAOMongo);
        core.start();
        core.waitUntilRunning();

        List<User> users = core.getUsers();
        List<Advertsment> advs = core.getAdvs();

        assertEquals(users.size(), 62);
        assertEquals(advs.size(), 26);
    }
}