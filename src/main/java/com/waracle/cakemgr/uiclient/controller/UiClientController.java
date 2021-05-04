package com.waracle.cakemgr.uiclient.controller;

import com.waracle.cakemgr.entities.Cake;
import com.waracle.cakemgr.uiclient.service.CakeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Profile("uiclient")
@Controller
public class UiClientController {

    @Autowired
    private CakeDataService cakeDataService;

    @GetMapping("/")
    public String displayCakeMainPage(Model model) {
        model.addAttribute("cakes", cakeDataService.getAllCakes());
        System.out.println(cakeDataService.getAllCakesAsJson());
        model.addAttribute("cakes_json", cakeDataService.getAllCakesAsJson());

        return "index";
    }
}
