package com.waracle.cakemgr.uiclient.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-uiclient.properties")
@ActiveProfiles("uiclient")
public class UiClientControllerTest {
    private static final String UICLIENT_URI = "http://localhost:8282/";

    RestTemplate restTemplate;

    @BeforeEach
    public void setupRestTemplate() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void checkThatApiServerReturnsCorrectText() {
        var uri = URI.create(UICLIENT_URI);

        var result = restTemplate.getForEntity(uri, String.class);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("UI Client", result.getBody());
    }
}
