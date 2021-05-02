package com.waracle.cakemgr.apiserver.service;

import com.waracle.cakemgr.entities.Cake;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CakeServiceTest {
    private CakeService cakeService;

    @BeforeEach
    public void setupCakeServiceToTest() {
        cakeService = new CakeService();
    }

    @Test
    public void checkThatCakeServiceReturnsCorrectCakes() {
        var cakeList = cakeService.getAllCakes();

        assertEquals(3, cakeList.size());
        assertTrue(validateCake(cakeList, 0));
    }

    private boolean validateCake(final List<Cake> cakeList, int index) {
        var cake = cakeList.get(index);
        return null != cake.id()
                && String.format("Cake %d", index+1).equals(cake.title())
                && String.format("This is cake %d", index+1).equals(cake.description())
                && String.format("http://cakes/%d.jpg", index+1).equals(cake.imageURL().toString());
    }

}
