package com.waracle.cakemgr.apiserver.utilities;

import com.waracle.cakemgr.entities.SeedDataCake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeedDataRetrieverTest {
    private SeedDataRetriever seedDataRetriever;

    @BeforeEach
    private void setupTestSubject() {
        seedDataRetriever = new SeedDataRetriever();
    }

    @Test
    public void checkThatSeedDataRetrieverReturnsCorrectSeedData() {
        var seedData = seedDataRetriever.getSeedData();

        assertEquals(20, seedData.size());

        var expectedCakes = generateExpectedSeedDataCakeTypes();
        assertEquals(seedData.get(0), expectedCakes.get(SeedDataCakeEnum.LEMON_CHEESECAKE));
        assertEquals(seedData.get(1), expectedCakes.get(SeedDataCakeEnum.VICTORIA_SPONGE));
        assertEquals(seedData.get(2), expectedCakes.get(SeedDataCakeEnum.CARROT_CAKE));
        assertEquals(seedData.get(3), expectedCakes.get(SeedDataCakeEnum.BANANA_CAKE));
        assertEquals(seedData.get(4), expectedCakes.get(SeedDataCakeEnum.BIRTHDAY_CAKE));
        assertEquals(seedData.get(5), expectedCakes.get(SeedDataCakeEnum.LEMON_CHEESECAKE));
        assertEquals(seedData.get(6), expectedCakes.get(SeedDataCakeEnum.VICTORIA_SPONGE));
        assertEquals(seedData.get(7), expectedCakes.get(SeedDataCakeEnum.CARROT_CAKE));
        assertEquals(seedData.get(8), expectedCakes.get(SeedDataCakeEnum.BANANA_CAKE));
        assertEquals(seedData.get(9), expectedCakes.get(SeedDataCakeEnum.BIRTHDAY_CAKE));
        assertEquals(seedData.get(10), expectedCakes.get(SeedDataCakeEnum.LEMON_CHEESECAKE));
        assertEquals(seedData.get(11), expectedCakes.get(SeedDataCakeEnum.VICTORIA_SPONGE));
        assertEquals(seedData.get(12), expectedCakes.get(SeedDataCakeEnum.CARROT_CAKE));
        assertEquals(seedData.get(13), expectedCakes.get(SeedDataCakeEnum.BANANA_CAKE));
        assertEquals(seedData.get(14), expectedCakes.get(SeedDataCakeEnum.BIRTHDAY_CAKE));
        assertEquals(seedData.get(15), expectedCakes.get(SeedDataCakeEnum.LEMON_CHEESECAKE));
        assertEquals(seedData.get(16), expectedCakes.get(SeedDataCakeEnum.VICTORIA_SPONGE));
        assertEquals(seedData.get(17), expectedCakes.get(SeedDataCakeEnum.CARROT_CAKE));
        assertEquals(seedData.get(18), expectedCakes.get(SeedDataCakeEnum.BANANA_CAKE));
        assertEquals(seedData.get(19), expectedCakes.get(SeedDataCakeEnum.BIRTHDAY_CAKE));
    }

    private enum SeedDataCakeEnum {
        LEMON_CHEESECAKE,
        VICTORIA_SPONGE,
        CARROT_CAKE,
        BANANA_CAKE,
        BIRTHDAY_CAKE;
    }

    private Map<SeedDataCakeEnum, SeedDataCake> generateExpectedSeedDataCakeTypes() {
        var seedDataCakeTypes = new HashMap<SeedDataCakeEnum, SeedDataCake>();
        seedDataCakeTypes.put(SeedDataCakeEnum.BANANA_CAKE,
                new SeedDataCake("Banana cake",
                        "Donkey kongs favourite",
                        "http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg"));
        seedDataCakeTypes.put(SeedDataCakeEnum.BIRTHDAY_CAKE,
                new SeedDataCake("Birthday cake",
                        "a yearly treat",
                        "http://cornandco.com/wp-content/uploads/2014/05/birthday-cake-popcorn.jpg"));
        seedDataCakeTypes.put(SeedDataCakeEnum.CARROT_CAKE,
                new SeedDataCake("Carrot cake",
                        "Bugs bunnys favourite",
                        "http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg"));
        seedDataCakeTypes.put(SeedDataCakeEnum.LEMON_CHEESECAKE,
                new SeedDataCake("Lemon cheesecake",
                        "A cheesecake made of lemon",
                        "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"));
        seedDataCakeTypes.put(SeedDataCakeEnum.VICTORIA_SPONGE,
                new SeedDataCake("victoria sponge",
                        "sponge with jam",
                        "http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg"));
        return seedDataCakeTypes;
    }
}
