package com.waracle.cakemgr.api;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.model.CakeSaveResponse;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Validated
@Api(value = "Cake Manager")
public interface CakeManagerApi {

    @GetMapping("/cakes")
    List<CakeEntity> allCakes() ;

    @PostMapping("/cakes")
    ResponseEntity<CakeSaveResponse> saveCakesInfo(@RequestBody CakeEntity cakeEntity);
}
