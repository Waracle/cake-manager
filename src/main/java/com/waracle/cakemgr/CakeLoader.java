package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CakeLoader implements ApplicationRunner {
    @Autowired
    CakeRepository cakeRepository;
    @Autowired
    ConfigProperties configProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final List<Cake> cakes = new ObjectMapper().readValue(configProperties.getPath(),
                new TypeReference<>() {
                });
        final Set uniqueCakes = new HashSet<>(cakes);

        cakeRepository.saveAll(uniqueCakes);
    }

}