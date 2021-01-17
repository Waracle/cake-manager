package com.waracle.cakemgr;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class CakeControllerTests {

    @LocalServerPort
    private int port;

    private String cakeUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CakeRepository cakeRepository;

    @BeforeEach
    public void setUp() {
        cakeUrl = "http://localhost:" + port + "/cakes";
    }

    @Test
    public void cakesShouldReturnElements() {
        final ResponseEntity<Cake[]> responseEntity = this.restTemplate.getForEntity(cakeUrl, Cake[].class);

        final Cake[] cakes = responseEntity.getBody();
        assertThat(cakes.length).isGreaterThan(1);
    }

    @Test
    public void cakeShouldBeSaved() {
        final String title = "test";
        final Cake newCake = Cake.builder().title(title).desc("desc").image("http://image.png").build();


        final ResponseEntity<Cake> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/cakes", newCake, Cake.class);
        final Cake savedCake = responseEntity.getBody();

        assertThat(savedCake.getId()).isPositive();
    }


    @Test
    public void cakeDuplicateShouldReturnConflict() {
        final String title = "test2";
        final Cake newCake = Cake.builder().title(title).desc("desc2").image("http://image2.png").build();

        this.restTemplate.postForEntity("http://localhost:" + port + "/cakes", newCake, Cake.class);

        final ResponseEntity<Cake> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/cakes", newCake, Cake.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CONFLICT.value());
    }
}




