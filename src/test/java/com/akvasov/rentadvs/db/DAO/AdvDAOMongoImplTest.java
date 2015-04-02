package com.akvasov.rentadvs.db.DAO;

import com.akvasov.rentadvs.db.DAO.MongoImpl.AdvDAOMongoImpl;
import com.akvasov.rentadvs.model.Advertsment;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by akvasov on 13.08.14.
 */
public class AdvDAOMongoImplTest {

    private static AdvDAOMongoImpl adDAO;

    @BeforeClass
    public static void init(){
        adDAO = new AdvDAOMongoImpl();
    }

    @Before
    public void setUp(){
        adDAO.clear();
    }

    @Test
    public void testAddAdvs() throws Exception {
        List lst = new ArrayList<>();
        for (int i = 0; i < 15; ++i){
            Advertsment a = new Advertsment();
            a.setId("advid 123_" + i);
            a.setText("text text text text text text text text text text_" + i);
            a.setUserId("user123_" + i);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(new Date().getTime() - 50000));
            calendar.add(Calendar.DAY_OF_MONTH, -i);

            a.setDate(calendar.getTime());
            lst.add(a);
        }

        adDAO.addAdvs(lst);

        Object[] ex = lst.toArray();
        Object[] ac = adDAO.loadAllAdv().toArray();
        assertArrayEquals(ex, ac);
    }

    @Test
    public void testClear() throws Exception {
        Advertsment a = new Advertsment();
        a.setId("advid 123");
        a.setText("text text text text text text text text text text");
        a.setUserId("user123");
        a.setDate(new Date());

        List lst = new ArrayList<>();
        lst.add(a);

        adDAO.addAdvs(lst);
        adDAO.clear();

        assertEquals(0, adDAO.loadAllAdv().size());
    }

    @Test
    public void testRemoveOlderWeekAdv() throws Exception {
        List lst = new ArrayList<>();
        List exp = new ArrayList<>();

        for (int i = 0; i < 15; ++i){
            Advertsment a = new Advertsment();
            a.setId("advid 123_" + i);
            a.setText("text text text text text text text text text text_" + i);
            a.setUserId("user123_" + i);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(new Date().getTime() - 50000));
            calendar.add(Calendar.DAY_OF_MONTH, -i);

            a.setDate(calendar.getTime());
            lst.add(a);

            if (i < 7){
                exp.add(a);
            }
        }

        adDAO.addAdvs(lst);
        adDAO.removeOlderWeekAdv();

        Object[] ex = exp.toArray();
        Object[] ac = adDAO.loadAllAdv().toArray();
        assertArrayEquals(ex, ac);
    }
}
