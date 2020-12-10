package com.waracle.cakemgr.service.impl;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.Cake;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.service.DaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CakeServiceImpl implements CakeService {

    @Autowired
    private DaoService daoService;

    @Override
    public List<Cake> parseCake(StringBuffer stringBuffer) throws IOException{
        JsonParser parser = new JsonFactory().createParser(stringBuffer.toString());
        if (JsonToken.START_ARRAY != parser.nextToken()) {
            throw new IOException("bad token");
        }
        JsonToken nextToken = parser.nextToken();
        List<Cake> cakes = new ArrayList<>();
        while (nextToken == JsonToken.START_OBJECT) {
            System.out.println("creating cake entity");

            Cake cake = new Cake();
            System.out.println(parser.nextFieldName());
            cake.setTitle(parser.nextTextValue());

            System.out.println(parser.nextFieldName());
            cake.setDesc(parser.nextTextValue());

            System.out.println(parser.nextFieldName());
            cake.setImage(parser.nextTextValue());



            boolean isCakeExists = daoService.checkCakeAlreadyExists(cake);

            if (!isCakeExists) {
                daoService.add(cake);
                Cake savedCake = daoService.getCake(cake.getId());

                cakes.add(savedCake);
            }

            nextToken = parser.nextToken();
            System.out.println(nextToken);

            nextToken = parser.nextToken();
            System.out.println(nextToken);
        }


        return cakes;
    }
}
