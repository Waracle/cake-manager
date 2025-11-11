package com.waracle.cakemgr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/cakes")
public class CakeController {

    private static final Logger logger = LoggerFactory.getLogger(CakeController.class);

    @Autowired
    private CakeRepository cakeRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadInitialData() {
        logger.info("Loading initial cake data...");

        try (InputStream inputStream = new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json").openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            logger.info("Parsing cake JSON data...");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode cakes = mapper.readTree(buffer.toString());

            for (JsonNode cakeNode : cakes) {
                try {
                    CakeEntity cake = new CakeEntity();
                    cake.setTitle(cakeNode.get("title").asText());
                    cake.setDescription(cakeNode.get("desc").asText());
                    cake.setImage(cakeNode.get("image").asText());

                    cakeRepository.save(cake);
                    logger.info("Added cake: {}", cake.getTitle());
                } catch (DataIntegrityViolationException ex) {
                    logger.warn("Duplicate cake entry, skipping: {}", cakeNode.get("title").asText());
                }
            }

            logger.info("Initial data loading completed");
        } catch (Exception ex) {
            logger.error("Error loading initial cake data", ex);
        }
    }

    @GetMapping
    public ResponseEntity<List<CakeEntity>> getAllCakes() {
        List<CakeEntity> cakes = cakeRepository.findAll();
        return ResponseEntity.ok(cakes);
    }

    @PostMapping
    public ResponseEntity<CakeEntity> createCake(@RequestBody CakeEntity cake) {
        CakeEntity savedCake = cakeRepository.save(cake);
        return ResponseEntity.ok(savedCake);
    }

}
