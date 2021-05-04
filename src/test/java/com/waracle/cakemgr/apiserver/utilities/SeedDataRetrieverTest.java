package com.waracle.cakemgr.apiserver.utilities;

import com.waracle.cakemgr.entities.SeedDataCake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SeedDataRetrieverTest {
    private SeedDataRetriever seedDataRetriever;

    @BeforeEach
    private void setupTestSubject() {
        seedDataRetriever = new SeedDataRetriever();
    }

    @Test
    public void checkThatSeedDataRetrieverReturnsCorrectSeedData() {
        var seedData = seedDataRetriever.getSeedData();

        assertEquals(5, seedData.size());

        assertTrue(seedData.contains(
                new SeedDataCake("Banana cake",
                        "Donkey kongs favourite",
                        "http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg")
        ));
        assertTrue(seedData.contains(
                new SeedDataCake("Birthday cake",
                        "a yearly treat",
                        "http://cornandco.com/wp-content/uploads/2014/05/birthday-cake-popcorn.jpg")
        ));
        assertTrue(seedData.contains(
                new SeedDataCake("Carrot cake",
                        "Bugs bunnys favourite",
                        "http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg")
        ));
        assertTrue(seedData.contains(
                new SeedDataCake("Lemon cheesecake",
                        "A cheesecake made of lemon",
                        "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
        ));
        assertTrue(seedData.contains(
                new SeedDataCake("victoria sponge",
                        "sponge with jam",
                        "http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg")
        ));
    }
}
