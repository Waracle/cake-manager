package com.waracle.cakemgr.controller;


import com.waracle.cakemgr.CakeEntity;
import com.waracle.cakemgr.service.DaoService;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CakeControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrlString;

    private URL base;

    private String cakesUrl;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private DaoService daoService;


    @BeforeEach
    public void setUp() throws Exception{
        this.base = new URL("http://localhost:" + port + "/");
        this.baseUrlString = "http://localhost:" + port + "/";
        this.cakesUrl = baseUrlString+"cakes";
    }

    @Test
    public void testGetAllCakes_SUCCESS(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // create request
        HttpEntity request = new HttpEntity(headers);

        // make a request
        ResponseEntity<String> response = new RestTemplate().exchange(baseUrlString, HttpMethod.GET, request, String.class);

        // get JSON response
        String jsonBodyResponse = response.getBody();

        assertNotNull("Response should not be null", response);
        assertNotNull("json body should not be null", jsonBodyResponse);
        assertTrue(jsonBodyResponse.contains("image"));

    }

    @Test
    public void testGetAllCakesInvalidUrl_FAIL(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // create request
        HttpEntity request = new HttpEntity(headers);


        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> {
                    // make a request
                    ResponseEntity<String> response = new RestTemplate().exchange(cakesUrl, HttpMethod.GET, request, String.class);
                }).withMessageContaining("Request method 'GET' not supported");
    }

    @Test
    public void testAddCake_SUCCESS(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        CakeEntity cake = new CakeEntity();
        cake.setTitle("cake name");
        cake.setDescription("cake description");
        cake.setImage("url");

        // create request
        HttpEntity request = new HttpEntity(cake, headers);

        // make a request
        ResponseEntity<String> response = new RestTemplate().exchange(cakesUrl, HttpMethod.POST, request, String.class);

        // get JSON response
        String jsonBodyResponse = response.getBody();

        assertNotNull("Response should not be null", response);
        assertNotNull("json body should not be null", jsonBodyResponse);
        assertTrue(jsonBodyResponse.contains("cake description"));

    }

    @Test
    public void testAddCakeWithNullObject_FAIL(){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");


        // create request
        HttpEntity request = new HttpEntity(null, headers);

        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> {
                    // make a request
                    ResponseEntity<String> response = new RestTemplate().exchange(cakesUrl, HttpMethod.POST, request, String.class);

                }).withMessageContaining("Required request body is missing:");


    }

}
