package com.waracle.cakemanager.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemanager.entity.Cake;
import com.waracle.cakemanager.service.CakeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class CakesDataConfig {
    @Bean
    CommandLineRunner loadStaticData(CakeService cakeService) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Cake>> typeReference = new TypeReference<>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/static/cakes.json");
            try {
                List<Cake> cakes = mapper.readValue(inputStream, typeReference);
                cakes.forEach(cakeService::addCake);
                System.out.println("Cakes Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save cakes: " + e.getMessage());
            }
        };
    }
}
