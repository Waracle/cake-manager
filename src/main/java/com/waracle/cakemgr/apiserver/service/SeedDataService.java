package com.waracle.cakemgr.apiserver.service;

import com.waracle.cakemgr.apiserver.persistance.*;
import com.waracle.cakemgr.apiserver.utilities.SeedDataRetriever;
import com.waracle.cakemgr.entities.SeedDataCake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("apiserver")
public class SeedDataService {

    @Autowired
    private SeedDataRetriever seedDataRetriever;

    @Autowired
    private CakeRepository cakeRepository;

    @PostConstruct
    public void populateDatabaseWithSeedData() {
        var seedData = seedDataRetriever.getSeedData();

        seedData.stream().map(this::convertSeedDataToCake)
                         .forEach(cakeRepository::save);
    }


    private CakeEntity convertSeedDataToCake(SeedDataCake cake) {
        var cakeEntity = new CakeEntity();
        cakeEntity.setTitle(cake.title());
        cakeEntity.setDescription(cake.desc());
        cakeEntity.setImageUrl(cake.image());
        return cakeEntity;
    }

}
