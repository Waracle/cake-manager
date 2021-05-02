package com.waracle.cakemgr.apiserver.controller;

import com.waracle.cakemgr.entities.Cake;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-apiserver.properties")
@ActiveProfiles("apiserver")
public class ApiServerControllerTest {
    private static final String APISERVER_URI = "http://localhost:8283/";

    RestTemplate restTemplate;

    private List<Cake> generatedCakes;
    
    @BeforeEach
    public void setupRestTemplate() {
        restTemplate = new RestTemplate();
        generatedCakes = new ArrayList<Cake>(3);
        generatedCakes.addAll(generateCakeList());
    }

    /**
     *
     * Checks that the / REST endpoint returns a list of cakes in human readable format.
     *
     * Data is sent from the endpoint in JSON format by default, the RestTemplate converts into the required format.
     */
    @Test
    public void checkThatApiServerRootContextReturnsCorrectCakeList() {
        var uri = URI.create(APISERVER_URI);

        var result = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cake>>() {
        });

        var cakes = result.getBody();

        System.out.println("Cake list... " + cakes);
        assertEquals(200, result.getStatusCodeValue());

        assertEquals(3, cakes.size());
    }
    
    private List<Cake> generateCakeList() {
        var expectedCakes = new ArrayList<Cake>();

        expectedCakes.add(new Cake(UUID.randomUUID(), "Cake 1", "This is cake 1", URI.create("http://cakes/1.png")));
        expectedCakes.add(new Cake(UUID.randomUUID(), "Cake 2", "This is cake 2", URI.create("http://cakes/2.gif")));
        expectedCakes.add(new Cake(UUID.randomUUID(), "Cake 3", "This is cake 3", URI.create("http://cakes/3.jpg")));

        return expectedCakes;
    }

    /**
     * Checks that the /cakes REST endpoint returns a list of cakes in JSON format.
     *
     * As Spring Boot automatically returns data via JSON by default, clients need to pass a request header to ensure
     * that the raw JSON is not converted into the format of the data by the RestTemplate.
     */
    @Test
    public void checkThatApiServerCakesContextReturnsCakeListAsJson() {
        var url = URI.create(APISERVER_URI + "cakes");

        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        var entity = new HttpEntity<>(headers);

        var result = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class);

        var cakes = result.getBody();

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());

    }
}