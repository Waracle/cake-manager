package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dao.Cake;
import com.waracle.cakemgr.dto.CakeRequestDto;
import com.waracle.cakemgr.mapper.CakeMapper;
import com.waracle.cakemgr.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WelcomeController {

  private final CakeService service;

  @Autowired
  public WelcomeController(CakeService service) {
    this.service = service;
  }

  @GetMapping("/")
  public String welcomePage(Model model) {
    model.addAttribute("cakes", service.getCakes());

    return "index";
  }

  @GetMapping("/cake")
  public String viewAddCakeHtml(Model model) {
    CakeRequestDto cake = new CakeRequestDto();
    model.addAttribute("cake", cake);
    return "addCake";
  }

  @PostMapping(path = "/cake")
  public String addCake( CakeRequestDto cake, Model model) {
    model.addAttribute("cake", cake);
    Cake cakeObj = CakeMapper.fromRequestDtoToEntity(cake);
    service.writeCake(cakeObj);

    return welcomePage(model);
  }

}
