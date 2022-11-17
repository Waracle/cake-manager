package com.waracle.cakemgr.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.constants.ApplicationConstants;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.exception.CakeManagerException;
import com.waracle.cakemgr.model.CakeSaveResponse;
import com.waracle.cakemgr.repository.CakeManagerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CakeManagerService {
    @Autowired
    private final CakeManagerRepository cakeManagerRepository;

    public CakeManagerService(CakeManagerRepository cakeManagerRepository){
        this.cakeManagerRepository = cakeManagerRepository;
    }

    Logger logger = LogManager.getLogger(CakeManagerService.class);

    /**
     * service to load initial data from external source to in memory data store.
     *
     * @throws CakeManagerException will be thrown with cause to the exception
     */
    public void initialize() throws CakeManagerException, IOException {
        logger.info("downloading cake json");
        JsonParser parser = null;
        try (InputStream inputStream = new URL(ApplicationConstants.URL).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder buffer = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }

            logger.info("parsing cake json");
            parser = new JsonFactory().createParser(buffer.toString());
            if (JsonToken.START_ARRAY != parser.nextToken()) {
                throw new CakeManagerException("bad token while parsing JSON");
            }

            JsonToken nextToken = parser.nextToken();
            processInitialData(parser, nextToken);

        } catch (Exception ex) {
            String errorMessage = "an exception occurred while processing initial data load.";
            logger.error(errorMessage);
            throw new CakeManagerException(errorMessage, ex.getCause());
        } finally {
            closeParser(parser);
        }
    }

    private void closeParser(JsonParser parser) throws IOException {
        if (parser != null) {
            try {
                parser.close();
            } catch (IOException e) {
                logger.error("Exception raised while closing the parser.");
                throw e;
            }
        }
    }

    /**
     * Parse JSON data received from external source and will prepare Cake Entity to write to database
     *
     * @param parser    JSON parser
     * @param nextToken JSON token
     * @throws IOException when parser read resulted in failure
     */
    private void processInitialData(JsonParser parser, JsonToken nextToken) throws IOException {
        while (nextToken == JsonToken.START_OBJECT) {
            logger.info("creating cake entity");

            String nextFieldName = parser.nextFieldName();
            CakeEntity cakeEntity = new CakeEntity();
            logger.info(nextFieldName);
            cakeEntity.setTitle(parser.nextTextValue());

            nextFieldName = parser.nextFieldName();
            logger.info(nextFieldName);
            cakeEntity.setDescription(parser.nextTextValue());

            nextFieldName = parser.nextFieldName();
            logger.info(nextFieldName);
            cakeEntity.setImage(parser.nextTextValue());

            persistData(cakeEntity);
            nextToken = parser.nextToken();
            logger.info(nextToken);

            nextToken = parser.nextToken();
            logger.info(nextToken);
        }
    }

    /**
     * Cake Entities will be persisted to in memory database.
     *
     * @param cakeEntity final cake entity to persist data to database
     */
    private void persistData(CakeEntity cakeEntity) {
        try {
            cakeManagerRepository.save(cakeEntity);
        } catch (ConstraintViolationException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * List all the cake products data available in database.
     *
     * @return List of cake products successfully retrieved (Status Code 200)
     * or bad request (status code 400)
     * or Not Found (status code 404)
     * or Internal server error (status code 500)
     * or Gateway Timeout (status code 504)
     */
    public List<CakeEntity> getCakes() {
        List<CakeEntity> cakesList = cakeManagerRepository.findAll();
        return Optional.ofNullable(cakesList).orElse(new ArrayList<>());
    }

    public ResponseEntity<CakeSaveResponse> saveCakes(CakeEntity cakeEntity) {
        CakeSaveResponse cakeSaveResponse = new CakeSaveResponse();
        if (cakeEntity == null) {
            cakeSaveResponse.setStatusCode(ApplicationConstants.STATUS_CODE_BAD_REQUEST);
            return new ResponseEntity<>(cakeSaveResponse, getHttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        try {
            cakeManagerRepository.save(cakeEntity);
            cakeSaveResponse.setStatus(ApplicationConstants.STATUS_SUCCESS);
            cakeSaveResponse.setCakeEntity(cakeEntity);
            cakeSaveResponse.setStatusCode(ApplicationConstants.STATUS_CODE_SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            cakeSaveResponse.setStatus(ApplicationConstants.STATUS_FAIL);
            throw e;
        }
        HttpHeaders headers = getHttpHeaders();
        return new ResponseEntity<>(cakeSaveResponse, headers, HttpStatus.OK);
    }

    private HttpHeaders getHttpHeaders() {
        return new HttpHeaders();

    }
}
