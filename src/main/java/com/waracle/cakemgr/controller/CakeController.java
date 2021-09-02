package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dao.Cake;
import com.waracle.cakemgr.dto.CakeRequestDto;
import com.waracle.cakemgr.dto.CakeResponseDto;
import com.waracle.cakemgr.mapper.CakeMapper;
import com.waracle.cakemgr.service.CakeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cakes")
public class CakeController {

  private final CakeService service;

  public CakeController(CakeService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<CakeResponseDto>> getCakes() {
    List<CakeResponseDto> cakes = service.getCakes().stream()
        .map(CakeMapper::fromEntityToResponseDto)
        .collect(Collectors.toList());

    return ResponseEntity.ok().body(cakes);
  }

  @PostMapping
  public ResponseEntity<String> postCake(@Valid @RequestBody CakeRequestDto cake) {
    Cake cakeObj = CakeMapper.fromRequestDtoToEntity(cake);
    service.writeCake(cakeObj);
    return ResponseEntity.ok().build();
  }
}
