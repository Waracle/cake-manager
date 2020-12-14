package com.waracle.cakemgr.controller;


import com.waracle.cakemgr.entity.Cake;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.service.DaoService;


import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CakeControllerTest {

    @LocalServerPort
    private int port;

    private String invalidCakesUrl;

    private URL baseUrl;

    private String cakesUrl;

    private String displayCakesUrl;

    @Mock
    private DaoService daoService;

    @Mock
    private RestTemplate template;

    @Mock
    private CakeService cakeService;


    @InjectMocks
    private CakeController cakeController;


    @BeforeEach
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:" + port + "/api");
        this.displayCakesUrl = baseUrl + "/";
        this.invalidCakesUrl = "http://localhost:" + port + "/cakes";
        this.cakesUrl = baseUrl + "/cakes";
    }

    @AfterEach
    public void tearDown() {
        this.baseUrl = null;
        this.displayCakesUrl = null;
        this.invalidCakesUrl = null;
        this.cakesUrl = null;
    }

    @Test
    public void testGetSavedCakes_SUCCESS() throws Exception {
        List<Cake> cakes = new ArrayList<>();
        Cake cake = new Cake();
        cake.setTitle("carrot");
        cake.setDesc("nice");
        cake.setImage("blah");
        cakes.add(cake);

        Mockito.when(daoService.getAllCakes()).thenReturn(cakes);


        ResponseEntity<List<Cake>> myEntity = new ResponseEntity<List<Cake>>(HttpStatus.ACCEPTED);
        Mockito.when(template.exchange(
                Matchers.eq(displayCakesUrl),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<List<Cake>>>any(),
                Matchers.<ParameterizedTypeReference<List<Cake>>>any())
        ).thenReturn(myEntity);

        List<Cake> res = cakeController.getSavedCakes();
        Assert.assertEquals(cake, res.get(0));

    }


    @Test
    public void testGetAllCakesInvalidUrl_FAIL() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // create request
        HttpEntity request = new HttpEntity(headers);


        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> {
                    // make a request
                    ResponseEntity<String> response = new RestTemplate().exchange(invalidCakesUrl, HttpMethod.GET, request, String.class);
                }).withMessageContaining("404");
    }


    @Test
    public void testAddCake_SUCCESS() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        Cake cake = new Cake();
        cake.setTitle("cake name");
        cake.setDesc("cake description");
        cake.setImage("url");


        Mockito.when(daoService.add(cake)).thenReturn(cake);


        ResponseEntity<Cake> myEntity = new ResponseEntity<Cake>(HttpStatus.ACCEPTED);
        Mockito.when(template.exchange(
                Matchers.eq(displayCakesUrl),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<Cake>>any(),
                Matchers.<ParameterizedTypeReference<Cake>>any())
        ).thenReturn(myEntity);

        Cake res = cakeController.addCake(cake);
        Assert.assertEquals(cake, res);
    }

    @Test
    public void testDownloadCakes_SUCCESS() {

        try {
            Set<Cake> res = cakeController.downloadCakes();
            verify(cakeService, atLeast(1)).parseCake(any());
        } catch (IOException ex) {
            fail("No exception should be thrown", ex);
        }

    }

    @Test
    public void testAddCakeWithNullObject_FAIL() {

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
