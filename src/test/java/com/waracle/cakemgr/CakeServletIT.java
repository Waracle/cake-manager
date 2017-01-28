package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class CakeServletIT {

    @Parameters
    public static Iterable<? extends Object> urls() {
        return asList("http://localhost:8282/", "http://localhost:8282/cakes");
    }

    @Parameter
    public String url;

    @Test
    public void generatesSyntacticallyValidJson() throws Exception {
        // When
        List<CakeEntity> cakes = httpGetJson(url);

        // Then
        assertThat(cakes).hasSize(20);
    }

    @Test
    public void presentsHtmlToHumans() throws Exception {
        String webPage = httpGetHtml(url);

        assertThat(webPage).contains("</form>");
        assertThat(webPage).contains("</html>");
    }

    private static InputStream httpGet(String url, String mimeType) throws IOException {
        // In real life, there's no chance i'd be faffing about like this with just the standard library. Apache HttpClient FTW!
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        c.setRequestMethod("GET");
        c.setRequestProperty("Accept", mimeType);
        return c.getInputStream();
    }

    private static List<CakeEntity> httpGetJson(String url) throws IOException {
        return new ObjectMapper().readValue(httpGet(url, "application/json"), new TypeReference<List<CakeEntity>>(){});
    }

    private static String httpGetHtml(String url) throws IOException {
        // NOTE: i couldn't remember the delimiter to use off the top of my head for this trick, had to google it to remind me because
        //       i never use this trick normally, i only know about it from a kata from years ago. Just trying to minimise deps
        //       i add to the project since you could solve this entire challenge using spring data rest and not write a single
        //       line of code!
        return new Scanner(httpGet(url, "text/html"),"UTF-8").useDelimiter("\\A").next();
    }
}
