package com.waracle.cakemgr.service.impl;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.entity.Cake;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.service.DaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Service
public class CakeServiceImpl implements CakeService {
    private static final Logger log = LoggerFactory.getLogger(CakeServiceImpl.class);

    @Autowired
    private DaoService daoService;

    @Override
    public Set<Cake> parseCake(StringBuffer stringBuffer) throws IOException{
        JsonParser parser = new JsonFactory().createParser(stringBuffer.toString());
        if (JsonToken.START_ARRAY != parser.nextToken()) {
            throw new IOException("bad token");
        }
        JsonToken nextToken = parser.nextToken();
        Set<Cake> cakes  = new HashSet<>();
        while (nextToken == JsonToken.START_OBJECT) {

            log.debug("creating cake entity");
            Cake cake = new Cake();
            log.debug("parsing title: {}", parser.nextFieldName());

            cake.setTitle(parser.nextTextValue());

            log.debug("parsing desc: {}", parser.nextFieldName());
            cake.setDesc(parser.nextTextValue());

            log.debug("parsing image: {}", parser.nextFieldName());
            cake.setImage(parser.nextTextValue());

            nextToken = parser.nextToken();
            log.debug("parsing token: {}", nextToken);

            nextToken = parser.nextToken();
            log.debug("parsing token: {}", nextToken);

            saveCake(cake);
            cakes.add(cake);
        }
        return cakes;
    }

    private void saveCake(Cake cake){
        if (!daoService.isCakeExists(cake)) {
            daoService.add(cake);
        }
    }
}
