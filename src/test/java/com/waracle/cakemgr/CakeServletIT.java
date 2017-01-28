package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.net.URL;
import java.util.List;

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
        // Given
        URL integrationTestingUrl = new URL(url);
        ObjectMapper om = new ObjectMapper();

        // When
        List<CakeEntity> cakes = om.readValue(integrationTestingUrl, new TypeReference<List<CakeEntity>>(){});

        // Then
        assertThat(cakes).hasSize(20);
    }
}
