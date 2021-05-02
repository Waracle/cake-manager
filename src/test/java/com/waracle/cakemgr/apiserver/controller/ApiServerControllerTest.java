package com.waracle.cakemgr.apiserver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-apiserver.properties")
@ActiveProfiles("apiserver")
public class ApiServerControllerTest {
    private static final String APISERVER_URI = "http://localhost:8283/";

    RestTemplate restTemplate;

    @BeforeEach
    public void setupRestTemplate() {
        restTemplate = new RestTemplate();
    }

    /**
     * Checks that the / REST endpoint returns a list of cakes in human readable format.
     *
     * Data is sent from the endpoint in JSON format by default, the RestTemplate converts into the required format.
     */
    @Test
    public void checkThatApiServerRootContextReturnsCorrectText() {
        var uri = URI.create(APISERVER_URI);

        var result = restTemplate.getForEntity(uri, List.class);

        var cakes = result.getBody();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(3, cakes.size());
        assertEquals("Cake1", cakes.get(0));
        assertEquals("Cake2", cakes.get(1));
        assertEquals("Cake3", cakes.get(2));
    }

    /**
     * Checks that the /cakes REST endpoint returns a list of cakes in JSON format.
     *
     * As Spring Boot automatically returns data via JSON by default, clients need to pass a request header to ensure
     * that the raw JSON is not converted into the format of the data by the RestTemplate.
     */
    @Test
    public void checkThatApiServerCakesContextReturnsCorrectCakeList() {
        var url = URI.create(APISERVER_URI + "cakes");

        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        var entity = new HttpEntity<>(headers);

        var result = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class);

        var cakes = result.getBody();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("[\"Cake1\",\"Cake2\",\"Cake3\"]", result.getBody());
    }
}