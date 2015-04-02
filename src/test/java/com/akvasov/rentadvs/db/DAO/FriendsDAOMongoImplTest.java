package com.akvasov.rentadvs.db.DAO;

import com.akvasov.rentadvs.db.DAO.MongoImpl.FriendsDAOMongoImpl;
import com.akvasov.rentadvs.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alex on 12.08.14.
 */
public class FriendsDAOMongoImplTest{

    private static FriendsDAOMongoImpl frDAO;

    @BeforeClass
    public static void init(){
        frDAO = new FriendsDAOMongoImpl();
    }

    @Before
    public void setUp(){
        frDAO.clear();
    }



    @Test
    public void testLoadAllUsers() throws Exception {
        List<User> lst = new ArrayList<>();

        for (int i = 0; i < 10; ++i){
            User u = new User();
            u.setId("userId123_" + i);
            u.setName("userNameField_" + i);
            u.setUserType(User.UserType.FRIENDS_FRIENDS_FRIEND);
            u.setFriendsIds(new ArrayList());
            for (int j = i; j <= i + 5; ++j){
                u.getFriendsIds().add(j);
            }
            lst.add(u);
        }

        frDAO.addUsers(lst);

        Object[] ex = lst.toArray();
        Object[] ac = frDAO.loadAllUsers().toArray();
        assertArrayEquals(ex, ac);
    }

    @Test
    public void testDeleteUsers() throws Exception {
        List<User> lst = new ArrayList<>();

        for (int i = 0; i < 10; ++i){
            User u = new User();
            u.setId("userId123_" + i);
            u.setName("userNameField_" + i);
            u.setUserType(User.UserType.FRIENDS_FRIENDS_FRIEND);
            u.setFriendsIds(new ArrayList());
            for (int j = i; j <= i + 5; ++j){
                u.getFriendsIds().add(i);
            }
            lst.add(u);
        }

        frDAO.addUsers(lst);

        User remove = lst.remove(0);
        frDAO.deleteUsers(lst);

        Object[] ex = {remove};
        Object[] ac = frDAO.loadAllUsers().toArray();
        assertArrayEquals(ex, ac);
    }

    @Test
    public void testAddUsers() throws Exception {
        List<User> lst = new ArrayList<>();

        User u = new User();
        u.setId("userId123");
        u.setName("userNameField");
        u.setUserType(User.UserType.FRIENDS_FRIEND);
        u.setFriendsIds(new ArrayList());
        for (int i = 1; i <= 5; ++i){
            u.getFriendsIds().add(i);
        }

        lst.add(u);
        frDAO.addUsers(lst);

        Object[] ex = lst.toArray();
        Object[] ac = frDAO.loadAllUsers().toArray();
        assertArrayEquals(ex, ac);
    }
}
