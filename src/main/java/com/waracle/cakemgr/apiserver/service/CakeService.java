package com.waracle.cakemgr.apiserver.service;

import com.waracle.cakemgr.entities.Cake;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CakeService {
    public List<Cake> getAllCakes() {
        var expectedCakes = new ArrayList<Cake>();

        expectedCakes.add(new Cake(UUID.randomUUID(), "Cake 1", "This is cake 1", URI.create("http://cakes/1.jpg")));
        expectedCakes.add(new Cake(UUID.randomUUID(), "Cake 2", "This is cake 2", URI.create("http://cakes/2.jpg")));
        expectedCakes.add(new Cake(UUID.randomUUID(), "Cake 3", "This is cake 3", URI.create("http://cakes/3.jpg")));

        return expectedCakes;
    }
 }
