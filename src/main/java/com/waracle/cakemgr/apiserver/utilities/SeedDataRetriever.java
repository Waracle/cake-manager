package com.waracle.cakemgr.apiserver.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entities.SeedDataCake;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
public class SeedDataRetriever {
    public Set<SeedDataCake> getSeedData() {
        final var seedDataCakes = new HashSet<SeedDataCake>();

        try {
            var objectMapper = new ObjectMapper();
            seedDataCakes.addAll(objectMapper.readValue(new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json"),
                    new TypeReference<Set<SeedDataCake>>() {}));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return seedDataCakes;
    }


}
