package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.api.CakeManagerApi;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.model.CakeSaveResponse;
import com.waracle.cakemgr.service.CakeManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@Validated
public class CakeManagerController implements CakeManagerApi {

    @Autowired
    CakeManagerService cakeManagerService;

    @Override
   public List<CakeEntity> allCakes(){
        return cakeManagerService.getCakes();
    }

    @Override
    public ResponseEntity<CakeSaveResponse> saveCakesInfo(@RequestBody CakeEntity cakeEntity){
        return cakeManagerService.saveCakes(cakeEntity);
    }
}
