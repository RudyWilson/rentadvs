package com.akvasov.rentadvs.backend.controller;

import com.akvasov.rentadvs.model.Advertsment;
import com.akvasov.rentadvs.model.User;

import java.util.List;

/**
 * Created by akvasov on 17.07.14.
 */
public interface PageController {

    /**
     * Gets and parse friends page
     *
     * @param userId - user identificator
     * @return list of friends by this user
     * @throws Exception
     */
    public List<User> getFriends(String userId) throws Exception;

    /**
     * Gets and parse users page and retrive advertsment
     * @param userId
     * @param offset
     * @return list advertsment
     */
    public List<Advertsment> getAdvertsments(String userId, Integer offset);

    /**
     * Gets and parse users page and retrieve advertsment
     * @param userId
     * @return
     */
    public List<Advertsment> getAdvertsments(String userId);
}
