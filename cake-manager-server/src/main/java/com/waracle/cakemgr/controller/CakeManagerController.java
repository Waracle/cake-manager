package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.service.CakeManagementService;
import com.waracle.manager.cake.api.CakesApi;
import com.waracle.manager.cake.model.Cake;
import com.waracle.manager.cake.model.CakeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
public class CakeManagerController implements CakesApi {

    private final CakeManagementService cakeService;

    @Override
    public ResponseEntity<CakeResponse> addCake(String title, String description, MultipartFile image) {
        CakeResponse response = cakeService.addCake(title, description, image);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Cake>> getCakes(Integer limit) {
        return ResponseEntity.ok(cakeService.getCakes());
    }
}
