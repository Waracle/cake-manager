package com.waracle.cakemanager.controller;

import com.waracle.cakemanager.entity.CakeEntity;
import com.waracle.cakemanager.exception.CakeAlreadyPresentException;
import com.waracle.cakemanager.exception.CakeNotAvailableException;
import com.waracle.cakemanager.service.CakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cakes")
@Slf4j
public class CakeManagerController {

    @Autowired
    CakeService cakeService;

    @GetMapping
    @PreAuthorize("hasRole('USER_ROLE')")
    public ResponseEntity<List<CakeEntity>> getAllCakes() throws CakeNotAvailableException {
        log.info("Get all cakes present in system");
        var cakeEntityList = cakeService.getAllCakes();
        return new ResponseEntity<List<CakeEntity>>((List<CakeEntity>) cakeEntityList, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public ResponseEntity<CakeEntity> createCake(@RequestBody CakeEntity cake) throws CakeAlreadyPresentException {
        log.info("Add new cake to the database :", cake.getTitle());
        CakeEntity savedCake = cakeService.saveCake(cake);
        return new ResponseEntity<>(savedCake, HttpStatus.CREATED);

    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public ResponseEntity<CakeEntity> updateCake(@RequestBody CakeEntity cake) {
        ResponseEntity responseEntity;
        CakeEntity updatedCake = cakeService.updateCake(cake.getCakeId(), cake);
        log.info("Updated the cake which is already present in system with title :" , updatedCake.getTitle());
        responseEntity = new ResponseEntity<CakeEntity>(updatedCake, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("cake/{id}")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public ResponseEntity<CakeEntity> deleteCakeById(@PathVariable("id") Long id) {
        CakeEntity deletedCake = cakeService.deleteCakeById(id);
        ResponseEntity responseEntity = new ResponseEntity<CakeEntity>(deletedCake, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/cake/{id}")
    @PreAuthorize("hasRole('USER_ROLE')")
    public ResponseEntity<CakeEntity> getCakeById(@PathVariable("id") Long id) throws CakeNotAvailableException {
        return new ResponseEntity<CakeEntity>(cakeService.getCakeById(id), HttpStatus.OK);
    }

    @PutMapping("cake/{id}")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public ResponseEntity<CakeEntity> updateCakeById(@PathVariable("id") Long id, @RequestBody CakeEntity cake) {
        ResponseEntity responseEntity;
        CakeEntity updatedCake = cakeService.updateCake(id, cake);
        responseEntity = new ResponseEntity<CakeEntity>(updatedCake, HttpStatus.OK);
        return responseEntity;
    }

}
