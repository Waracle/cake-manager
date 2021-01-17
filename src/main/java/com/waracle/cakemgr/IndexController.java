package com.waracle.cakemgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private CakeRepository cakeRepository;

    @GetMapping("/")
    public String Cakes(Model model) {
        List<Cake> cakes = cakeRepository.findAll();
        model.addAttribute("cakes", cakes);
        model.addAttribute("cake", new Cake());
        return "index";
    }

    @PostMapping("/newCake")
    public String newCake(@ModelAttribute Cake cake, Model model) {
        try {
            model.addAttribute("cake", cakeRepository.save(cake));
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Error creating new cake");
        }
        return "newCake";
    }
}