package com.waracle.cakemgr.apiserver.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entities.SeedDataCake;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SeedDataRetriever {
    public List<SeedDataCake> getSeedData() {
        final var seedDataCakes = new ArrayList<SeedDataCake>();

        try {
            var objectMapper = new ObjectMapper();
            seedDataCakes.addAll(objectMapper.readValue(new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json"),
                    new TypeReference<List<SeedDataCake>>() {}));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seedDataCakes;
    }


}
