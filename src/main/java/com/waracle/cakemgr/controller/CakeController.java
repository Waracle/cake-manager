package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cakes")
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @EventListener(ApplicationReadyEvent.class)
    public void loadInitialData() {
        cakeService.loadInitialData();
    }

    @GetMapping
    public ResponseEntity<List<CakeDto>> getAllCakes() {
        List<CakeDto> cakes = cakeService.getAllCakes();
        return ResponseEntity.ok(cakes);
    }

    @PostMapping
    public ResponseEntity<CakeDto> createCake(@RequestBody CakeDto cakeDto) {
        CakeDto savedCake = cakeService.createCake(cakeDto);
        return ResponseEntity.ok(savedCake);
    }

}
