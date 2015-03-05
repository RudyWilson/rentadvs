package com.akvasov.rentadvs.db.DAO.MongoImpl;

import com.akvasov.rentadvs.db.DAO.FriendsDAO;
import com.akvasov.rentadvs.model.User;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akvasov on 17.07.14.
 */
public class FriendsDAOMongoImpl extends MongoCommonDAO implements FriendsDAO {

    @Override
    public List<User> loadAllUsers() {
        List<User> result = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            User u = User.unMarshal(dbObjects.next());
            result.add(u);
        }
        return result;
    }

    @Override
    public void deleteUsers(List<User> users) {
        for (User u : users) {
            collection.remove(new BasicDBObject("id", u.getId()));
        }
    }

    @Override
    public void addUsers(List<User> users) {
        for (User u : users) {
            try {
                collection.update(new BasicDBObject("id", u.getId()), new BasicDBObject("$set", u.marshal()), true, true);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void clear() {
        collection.drop();
    }

    @Override
    protected String getCollection() {
        return "friends";
    }
}
