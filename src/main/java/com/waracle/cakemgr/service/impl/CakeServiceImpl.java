package com.waracle.cakemgr.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.util.CakeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CakeServiceImpl implements CakeService {

    private static final Logger logger = LoggerFactory.getLogger(CakeServiceImpl.class);

    @Autowired
    private CakeRepository cakeRepository;

    @Autowired
    private CakeMapper cakeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CakeDto> getAllCakes() {
        return cakeRepository.findAll()
            .stream()
            .map(cakeMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public CakeDto createCake(CakeDto cakeDto) {
        CakeEntity cake = cakeMapper.toEntity(cakeDto);
        CakeEntity savedCake = cakeRepository.save(cake);
        return cakeMapper.toDto(savedCake);
    }

    @Override
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
}
