package com.akvasov.rentadvs.db.DAO.MongoImpl;

import com.akvasov.rentadvs.db.DAO.AdvDAO;
import com.akvasov.rentadvs.model.Advertsment;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by akvasov on 13.08.14.
 */
public class AdvDAOMongoImpl extends MongoCommonDAO implements AdvDAO {

    @Override
    public void addAdvs(List<Advertsment> advs) {
        for (Advertsment a : advs) {
            try {
                collection.update(new BasicDBObject("id", a.getId()), new BasicDBObject("$set", a.marshal()), true, true);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Advertsment> loadAllAdv() {
        List<Advertsment> result = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            Advertsment a = Advertsment.unMarshal(dbObjects.next());
            result.add(a);
        }
        return result;
    }

    @Override
    public void removeOlderWeekAdv() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date weekAgo = calendar.getTime();

        collection.remove(new BasicDBObject("date", new BasicDBObject("$lt", weekAgo)));
    }

    @Override
    public void clear() {
        collection.drop();
    }

    @Override
    protected String getCollection() {
        return "advertsment";
    }
}
