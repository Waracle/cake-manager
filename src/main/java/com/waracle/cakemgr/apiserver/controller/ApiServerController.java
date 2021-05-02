package com.waracle.cakemgr.apiserver.controller;

import com.waracle.cakemgr.apiserver.service.CakeService;
import com.waracle.cakemgr.entities.Cake;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Profile("apiserver")
@RestController
public class ApiServerController {
    private CakeService cakeService;

    public ApiServerController(CakeService cakeService) {
        this.cakeService = cakeService;
    }

    @GetMapping("/")
    public List<Cake> retrieveCakesInHumanReadableForm() {
        return retrieveListOfCakes();
    }

    @GetMapping("/cakes")
    public List<Cake> retrieveCakesInJSONForm() {
        return retrieveListOfCakes();
    }

    private List<Cake> retrieveListOfCakes() {
        return cakeService.getAllCakes();
    }

}
