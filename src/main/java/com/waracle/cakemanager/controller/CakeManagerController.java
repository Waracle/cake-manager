package com.waracle.cakemanager.controller;

import com.waracle.cakemanager.entity.CakeEntity;
import com.waracle.cakemanager.exception.CakeAlreadyPresentException;
import com.waracle.cakemanager.exception.CakeNotAvailableException;
import com.waracle.cakemanager.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cakes")
public class CakeManagerController {

    @Autowired
    CakeService cakeService;

    @GetMapping
    public ResponseEntity<List<CakeEntity>> getAllCakes() throws CakeNotAvailableException {
        var cakeEntityList = cakeService.getAllCakes();
        return new ResponseEntity<List<CakeEntity>>((List<CakeEntity>) cakeEntityList, HttpStatus.OK);
    }

    @GetMapping("/cake/{id}")
    public ResponseEntity<CakeEntity> getCakeById(@PathVariable("id") Long id) throws CakeNotAvailableException {
        return new ResponseEntity<CakeEntity>(cakeService.getCakeById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CakeEntity> createCake(@RequestBody CakeEntity cake) throws CakeAlreadyPresentException {
        CakeEntity savedCake = cakeService.saveCake(cake);
        return new ResponseEntity<>(savedCake, HttpStatus.CREATED);

    }

    @PutMapping("cake/{id}")
    public ResponseEntity<CakeEntity> updateCake(@PathVariable("id") Long id, @RequestBody CakeEntity cake) {
        ResponseEntity responseEntity;
        CakeEntity updatedCake = cakeService.updateCake(id, cake);
        responseEntity = new ResponseEntity<CakeEntity>(updatedCake, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("cake/{id}")
    public ResponseEntity<CakeEntity> deleteCake(@PathVariable("id") Long id) {
        CakeEntity deletedCake = cakeService.deleteCakeById(id);
        ResponseEntity responseEntity = new ResponseEntity<CakeEntity>(deletedCake, HttpStatus.OK);
        return responseEntity;
    }
}
