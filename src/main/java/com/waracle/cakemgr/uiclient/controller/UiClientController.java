package com.waracle.cakemgr.uiclient.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Profile("uiclient")
@RestController
public class UiClientController {
    @GetMapping("/")
    public String test() {
        return "UI Client";
    }

}
