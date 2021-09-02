package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

  private final CakeService service;

  @Autowired
  public WelcomeController(CakeService service) {
    this.service = service;
  }

  @GetMapping("/")
  public String displayCakeMainPage(Model model) {
    model.addAttribute("cakes", service.getCakes());

    return "index";
  }


}
