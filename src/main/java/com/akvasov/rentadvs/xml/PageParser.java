package com.akvasov.rentadvs.xml;

import com.akvasov.rentadvs.model.Advertsment;
import com.akvasov.rentadvs.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alex on 21.06.14.
 */
public class PageParser {
    private static Pattern friendsJson = Pattern.compile("(.*)(\\{.*\\})(.*)");

    public static List<User> parseFriends(String page) throws Exception {
        List answer = new ArrayList<User>();

        page = page.replaceAll("\\s+", " ");

        Matcher friendsJsonMatcher = friendsJson.matcher(page);
        if (friendsJsonMatcher.matches()){
            String friendsJson = friendsJsonMatcher.group(2);
            String[] splited = friendsJson.split("\\[");
            for (int i = 1; i < splited.length; i++){
                if (splited[i].length() < 5) continue;
                String[] usrPrmtrs = splited[i].split(",");

                if (usrPrmtrs.length < 5) {
                    continue;
                    //throw new RuntimeException("user parse invalid[" + splited[i] + "]");
                }

                User user = new User();
                user.setId(usrPrmtrs[0].toString());
                user.setName(usrPrmtrs[5]);

                answer.add(user);
            }
        }

        return answer;
    }

    public static List<Advertsment> parseAdvertsments(String userId, Integer offset){
        List answer = new ArrayList<Advertsment>();

        return answer;
    }

}
