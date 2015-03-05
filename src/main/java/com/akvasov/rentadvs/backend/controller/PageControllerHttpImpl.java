package com.akvasov.rentadvs.backend.controller;

import com.akvasov.rentadvs.backend.http.HttpDao;
import com.akvasov.rentadvs.model.Advertsment;
import com.akvasov.rentadvs.model.User;
import com.akvasov.rentadvs.xml.PageParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 21.06.14.
 */
public class PageControllerHttpImpl implements PageController{

    public List<User> getFriends(String userId) throws Exception {
        String friendsPage = HttpDao.getFriends(userId);
        List<User> users = PageParser.parseFriends(friendsPage);
       return users;
    }

    public List<Advertsment> getAdvertsments(String userId, Integer offset){
        List answer = new ArrayList<Advertsment>();

        return answer;
    }

    public List<Advertsment> getAdvertsments(String userId){
        return getAdvertsments(userId, 10);
    }
}
