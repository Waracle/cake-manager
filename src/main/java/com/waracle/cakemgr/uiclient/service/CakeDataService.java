package com.waracle.cakemgr.uiclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entities.Cake;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CakeDataService {
    public List<Cake> getAllCakes() {
        return generateCakes();
    }

    public String getAllCakesAsJson() {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(generateCakes());
        } catch (JsonProcessingException e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private List<Cake> generateCakes() {
        var cakes = new ArrayList<Cake>();
        cakes.add(new Cake(UUID.fromString("11111111-1111-1111-1111-111111111111"), "title 1", "title 1", URI.create("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")));
        cakes.add(new Cake(UUID.fromString("22222222-2222-2222-2222-222222222222"), "title 2", "title 2", URI.create("http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg")));
        cakes.add(new Cake(UUID.fromString("33333333-3333-3333-3333-333333333333"), "title 3", "title 3", URI.create("http://cornandco.com/wp-content/uploads/2014/05/birthday-cake-popcorn.jpg")));
        return cakes;
    }
}
