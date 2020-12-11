package com.waracle.cakemgr.service.impl;



import com.waracle.cakemgr.Cake;
import com.waracle.cakemgr.service.DaoService;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertNull;


@SpringBootTest
public class DaoServiceImplTest {

    @Autowired
    private DaoService daoService;

    @Test
    public void testAddCake_SUCCESS(){
        Cake cake = createCake("title", "desc", "image");

        daoService.add(cake);

        Cake savedCake = daoService.getCakeByTitle("title");
        assertNotNull("Response should not be null", savedCake);
        assertEquals(cake.getTitle(), savedCake.getTitle());
        assertEquals(cake.getDesc(), savedCake.getDesc());
        assertEquals(cake.getImage(), savedCake.getImage());

        daoService.deleteCake(savedCake);
        assertNull("Cake should be null", daoService.getCakeByTitle("title"));
    }

    @Test
    public void testGetAllCakes_SUCCESS(){
        Cake cake1 = createCake("title1", "desc1", "image1");
        Cake cake2 = createCake("title2", "desc2", "image2");

        daoService.add(cake1);
        daoService.add(cake2);

        List<Cake> cakes = daoService.getAllCakes();
        assertEquals(2, cakes.size());
        assertEquals(cakes.get(0).getTitle(), "title1");
        assertEquals(cakes.get(0).getDesc(), "desc1");
        assertEquals(cakes.get(0).getImage(), "image1");

        assertEquals(cakes.get(1).getTitle(), "title2");
        assertEquals(cakes.get(1).getDesc(), "desc2");
        assertEquals(cakes.get(1).getImage(), "image2");


        daoService.deleteCake(cakes.get(0));
        daoService.deleteCake(cakes.get(1));
        assertNull("Cake should be null", daoService.getCakeByTitle("title1"));
        assertNull("Cake should be null", daoService.getCakeByTitle("title2"));
    }

    @Test
    public void testGetCake_SUCCESS(){
        Cake cake1 = createCake("title1", "desc1", "image1");

        daoService.add(cake1);

        Cake savedCake = daoService.getCakeByTitle("title1");

        assertEquals(savedCake.getTitle(), "title1");
        assertEquals(savedCake.getDesc(), "desc1");
        assertEquals(savedCake.getImage(), "image1");

        daoService.deleteCake(savedCake);
        assertNull("Cake should be null", daoService.getCakeByTitle("title1"));

    }

    private Cake createCake(String title, String desc, String image){
        Cake cake = new Cake();
        cake.setTitle(title);
        cake.setDesc(desc);
        cake.setImage(image);
        return cake;
    }

}
