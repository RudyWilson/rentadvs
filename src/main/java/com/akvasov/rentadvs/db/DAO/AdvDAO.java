package com.akvasov.rentadvs.db.DAO;

import com.akvasov.rentadvs.model.Advertsment;

import java.util.List;

/**
 * Created by akvasov on 07.08.14.
 */
public interface AdvDAO {

    void addAdvs(List<Advertsment> advs);

    List<Advertsment> loadAllAdv();

    void removeOlderWeekAdv();

    void clear();
}
