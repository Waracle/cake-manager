package com.waracle.cakemgr.apiserver.service;

import com.waracle.cakemgr.apiserver.persistance.CakeEntity;
import com.waracle.cakemgr.apiserver.persistance.CakeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-apiserver.properties")
@ActiveProfiles("apiserver")
public class SeedDataServiceTest {
    @Autowired
    private CakeRepository cakeRepository;

    @Test
    public void checkSeedDataServicePopulatesDataCorrectly() {
        var populatedCakes = cakeRepository.findAll();

        assertEquals(5, populatedCakes.size());

        checkPopulatedCakeHasCorrectData(populatedCakes.get(0),
                "Lemon cheesecake",
                "A cheesecake made of lemon",
                "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg");

        checkPopulatedCakeHasCorrectData(populatedCakes.get(1),
                "victoria sponge",
                "sponge with jam",
                "http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg");

        checkPopulatedCakeHasCorrectData(populatedCakes.get(2),
                "Carrot cake",
                "Bugs bunnys favourite",
                "http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg");

        checkPopulatedCakeHasCorrectData(populatedCakes.get(3),
                "Birthday cake",
                "a yearly treat",
                "http://cornandco.com/wp-content/uploads/2014/05/birthday-cake-popcorn.jpg");

        checkPopulatedCakeHasCorrectData(populatedCakes.get(4),
                "Banana cake",
                "Donkey kongs favourite",
                "http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg");
    }

    private void checkPopulatedCakeHasCorrectData(CakeEntity cake, String title, String description, String imageUrl) {
        assertNotNull(cake.getId());
        assertEquals(title, cake.getTitle());
        assertEquals(description, cake.getDescription());
        assertEquals(imageUrl, cake.getImageUrl());
    }
}
