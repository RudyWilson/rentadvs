package com.akvasov.rentadvs.backend.http;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;

/**
 * Created by alex on 21.06.14.
 */
public class HttpDao {

    public static String getFriends(String id) throws Exception {
        Request request = OurHttpClient.getInstance().makePostRequest("http://vk.com/al_friends.php");

        ContentResponse response = request
                .header("Referer", "http://vk.com/" + id)
                //act=load_friends_silent&al=1&gid=0&id=47735
                .param("act", "load_friends_silent")
                .param("al", "1")
                .param("gid", "0")
                .param("id", id)

                .send();

        String answer = response.getContentAsString();
        System.out.println("answer = " + answer);
        return answer;
    }

    public static String getPage(String id, Integer offset) throws Exception {
        Request request = OurHttpClient.getInstance().makePostRequest("http://vk.com/" + id);

        ContentResponse response = request
                .header("Referer", "http://vk.com/" + id)

                .param("act", "get_wall")
                .param("al", "1")
                .param("fixed", "")
                .param("offset", offset.toString())
                .param("owner_id", id)
                .param("type", "all")

                .send();

        return response.getContentAsString();
    }

    public static String getPage(String id) throws Exception {
        return getPage(id, 10);
    }


}
