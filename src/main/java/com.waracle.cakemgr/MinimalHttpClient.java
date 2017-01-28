package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class MinimalHttpClient {
    public static InputStream httpGet(String url, String mimeType) throws IOException {
        // In real life, there's no chance i'd be faffing about like this with just the standard library. Apache HttpClient FTW!
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        c.setRequestMethod("GET");
        c.setRequestProperty("Accept", mimeType);
        return c.getInputStream();
    }

    public static List<CakeEntity> httpGetJson(String url) throws IOException {
        return new ObjectMapper().readValue(httpGet(url, "application/json"), new TypeReference<List<CakeEntity>>(){});
    }

    public static String httpGetHtml(String url) throws IOException {
        // NOTE: i couldn't remember the delimiter to use off the top of my head for this trick, had to google it to remind me because
        //       i never use this trick normally, i only know about it from a kata from years ago. Just trying to minimise deps
        //       i add to the project since you could solve this entire challenge using spring data rest and not write a single
        //       line of code!
        return new Scanner(httpGet(url, "text/html"),"UTF-8").useDelimiter("\\A").next();
    }
}
