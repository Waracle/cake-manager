package com.waracle.cakemgr.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.dao.CakeEntity;
import com.waracle.cakemgr.dao.CakeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;

@Service
public class CakeService {

  @Value("${cake.url}")
  private String cakeUrl;

  private final CakeRepository repository;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public CakeService(CakeRepository repository) {
    this.repository = repository;
  }

  @PostConstruct
  public void populateCakes() throws IOException {
    Set<CakeEntity> cakeEntities = objectMapper
        .readValue(new URL(cakeUrl), new TypeReference<>() {
        });
    repository.saveAll(cakeEntities);
  }

  public List<CakeEntity> getCakes() {

    return repository.findAll();
  }

  public void writeCake(CakeEntity cakeEntity) {
    repository.save(cakeEntity);
  }
}
