package com.akvasov.rentadvs.backend.controller;

import com.akvasov.rentadvs.model.Advertsment;
import com.akvasov.rentadvs.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by akvasov on 17.07.14.
 */
public class PageControllerTestStaticImpl implements PageController{

    private static Logger LOGGER = Logger.getLogger(PageControllerTestStaticImpl.class.getName());

    private List<List<String>> advs = new ArrayList<>();
    private List<List<Integer>> frnds = new ArrayList<>();
    private Integer cnt;

    public PageControllerTestStaticImpl() {
        System.out.println("Load static data for PageControllerTestStaticImpl");
        File file;
        Scanner scanner = null;
        try {
            file = new File(PageControllerTestStaticImpl.class.getResource("/test/testdata.in").toURI());
            scanner = new Scanner(file);
        } catch (URISyntaxException | FileNotFoundException e) {
            System.err.println("Failed open file :(");
            e.printStackTrace();
        }

        cnt = scanner.nextInt();
        System.out.println("Friends count: " + cnt);

        for (int i = 0; i < cnt; ++i){
            frnds.add(new ArrayList<Integer>());
            for (int j = 0; j < cnt; ++j){
                int x = scanner.nextInt();
                if (x == 1){
                    frnds.get(i).add(j);
                }
            }

            System.out.println("User " + i + ": " + frnds.get(i));
        }

        System.out.println("Advs");

        scanner.nextLine();
        for (int i = 0; i < cnt; ++i){
            advs.add(new ArrayList<>());

            String s = scanner.nextLine();
            if (!(s.startsWith("[") && s.endsWith("]"))){
                throw new RuntimeException("Error parsing input file");
            }
            s = s.substring(1, s.length() - 1);
            if (!s.isEmpty()){
                advs.get(i).addAll(Arrays.asList(s.split("(, )")));
            }

            System.out.println("User " + i + " (" + advs.get(i).size() + "): " + advs.get(i));
        }
    }

    @Override
    public List<User> getFriends(String userId) throws Exception {
        List<User> result = new ArrayList();
        List<Integer> fr = frnds.get(Integer.parseInt(userId));
        for (Integer i: fr){
            User u = new User(i.toString(), "user " + i);

            if (frnds.get(0).contains(i)){
                u.setUserType(User.UserType.FRIEND);
            } else {
                u.setUserType(User.UserType.FRIENDS_FRIEND);
            }

            result.add(u);
        }
        LOGGER.fine("Get friends for userID=" + userId + " : " + result);
        return result;
    }

    @Override
    public List<Advertsment> getAdvertsments(String userId, Integer offset) {
        throw new UnsupportedOperationException();
        //return null;
    }

    @Override
    public List<Advertsment> getAdvertsments(String userId) {
        List<Advertsment> result = new ArrayList<>();
        for (String s: advs.get(Integer.parseInt(userId))){
            Advertsment a = new Advertsment();
            a.setUserId(s.substring(5));
            a.setText("user with id " + a.getUserId() + " on user with id " + userId + " advertsment text");
            a.setDate(new Date());
            a.setType(1);
            a.setId(s.substring(5));

            result.add(a);
        }
        LOGGER.fine("Get adv for userID=" + userId + " : " + result);
        return result;
    }
}
