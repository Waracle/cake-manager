package com.waracle.cakemgr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class IndexControllerTest {

    @LocalServerPort
    private int port;

    private String cakeUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CakeRepository cakeRepository;

    @BeforeEach
    public void setUp() {
        cakeUrl = "http://localhost:" + port + "/";
    }

    @Test
    public void cakesShouldRender() {
        final ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(cakeUrl, String.class);

        final String html = responseEntity.getBody();
        assertThat(html).contains("Cakes");
    }

    @Test
    public void newCakeShouldCreateACake() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("title", "newCake");
        map.add("image", "http://image.png");
        map.add("desc", "desc");

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        final ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(cakeUrl + "newCake",
                request, String.class);

        final String html = responseEntity.getBody();
        assertThat(html).contains("newCake");
    }

    @Test
    public void newCakeShouldHandleErrors() {
        final ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(cakeUrl + "newCake", "", String.class);

        final String html = responseEntity.getBody();
        assertThat(html).contains("Cakes");
    }
}