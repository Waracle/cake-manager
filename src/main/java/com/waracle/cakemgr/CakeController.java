package com.waracle.cakemgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CakeController {

    @Autowired
    private CakeRepository cakeRepository;

    @GetMapping("/cakes")
    public List<Cake> Cakes() {
        return cakeRepository.findAll();
    }

    @PostMapping("/cakes")
    public Cake Cake(@RequestBody Cake cake) {
        try {
            return cakeRepository.save(cake);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException();
        }
    }
}