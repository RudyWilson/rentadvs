package com.akvasov.rentadvs.backend.http;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by alex on 21.06.14.
 */
public final class OurHttpClient {
    private static OurHttpClient instance = null;

    private HttpClient client = null;

    private OurHttpClient() throws Exception {
        client = new HttpClient();
        client.start();
    }

    public static synchronized OurHttpClient getInstance() throws Exception {
        if (instance == null) {
            instance = new OurHttpClient();
        }
        return instance;
    }

    public void stop() throws Exception {
        client.stop();
    }

    private Request setDefaultHeader(Request inputRequest){
        Request outputRequest = inputRequest
                .header("Content-Length", "58")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Cache-Control", "no-cache")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("Cookie", "remixlang=0; remixflash=11.2.202; remixscreen_depth=24; remixdt=0; remixseenads=2; audio_vol=18; remixstid=1000526503_4ff161bd965ca6183e; remixrefkey=499d8a4c130e08393d; remixno_chrome_bar_ff=1; remixtst=53a08993; remixsid=b44eec398317b8842b67472aa1756a89caab02b2e3fb72d744ecb; remixhidemchg=1")
                .header("Host", "vk.com")
                .header("Pragma", "no-cache")
                //.header("Referer", "http://vk.com/id47735")
                .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:30.0) Gecko/20100101 Firefox/30.0")
                .header("X-Requested-With", "XMLHttpRequest");

        return outputRequest;
    }

    public ContentResponse sendGetRequest(String uri) throws InterruptedException, ExecutionException, TimeoutException {
        ContentResponse response = client.GET(uri);
        return response;
    }

    public Request makePostRequest(String uri) {
        Request request = client.POST("http://vk.com/al_friends.php");
        return setDefaultHeader(request);
    }

}
