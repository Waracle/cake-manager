package com.waracle.cakemgr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;

import static com.waracle.cakemgr.MinimalHttpClient.httpGetHtml;
import static com.waracle.cakemgr.MinimalHttpClient.httpGetJson;
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

        assertThat(webPage).contains("</html>");
    }
}
