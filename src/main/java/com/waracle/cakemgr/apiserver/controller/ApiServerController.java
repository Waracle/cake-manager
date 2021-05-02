package com.waracle.cakemgr.apiserver.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Profile("apiserver")
@RestController
public class ApiServerController {
    @GetMapping("/")
    public List<String> test() {
        return retrieveListOfCakes();
    }

    @GetMapping("/cakes")
    public List<String> retrieveCakesInJSONForm() {
        return retrieveListOfCakes();
    }

    private List<String> retrieveListOfCakes() {
        var listOfCakes = new ArrayList<String>();

        listOfCakes.add("Cake1");
        listOfCakes.add("Cake2");
        listOfCakes.add("Cake3");

        return listOfCakes;
    }

}
