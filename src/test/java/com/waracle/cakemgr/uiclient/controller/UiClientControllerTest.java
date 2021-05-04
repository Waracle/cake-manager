package com.waracle.cakemgr.uiclient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entities.Cake;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-uiclient.properties")
@ActiveProfiles("uiclient")
public class UiClientControllerTest {
    private static final String UICLIENT_URI = "http://localhost:8282/";

    RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @BeforeEach
    public void setupRestTemplate() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void checkThatUIClientInstantiatesThymeleafMainPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkThatMainPageContainsCorrectHeaderText() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Welcome to the Cake Manager application!")))
                .andExpect(content().string(containsString("My UI design skills suck by the way")));
    }

    @Test
    public void checkThatMainPageContainsCorrectCakeTableData() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("cakes", Matchers.equalTo(generateTestData())));
    }

    @Test
    public void checkThatMainPageContainsCorrectCakeJsonData() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("cakes_json", generateTestDataAsJson()));
    }

    private List<Cake> generateTestData() {
        var cakes = new ArrayList<Cake>();
        cakes.add(new Cake(UUID.fromString("11111111-1111-1111-1111-111111111111"), "title 1", "title 1", URI.create("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")));
        cakes.add(new Cake(UUID.fromString("22222222-2222-2222-2222-222222222222"), "title 2", "title 2", URI.create("http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg")));
        cakes.add(new Cake(UUID.fromString("33333333-3333-3333-3333-333333333333"), "title 3", "title 3", URI.create("http://cornandco.com/wp-content/uploads/2014/05/birthday-cake-popcorn.jpg")));
        return cakes;
    }

    private String generateTestDataAsJson() {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(generateTestData());
        } catch (JsonProcessingException e) {
            System.out.println("ERRROR > " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

}
