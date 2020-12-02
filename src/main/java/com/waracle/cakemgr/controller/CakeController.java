package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.Cake;
import com.waracle.cakemgr.service.DaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CakeController {

    @Autowired
    private DaoService daoService;

    @GetMapping("/cakes")
    public List<Cake> getAllCakes(){

        List<Cake> cakes = new ArrayList<>();
        System.out.println("downloading cake json");
        try (InputStream inputStream = new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json").openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }

            System.out.println("parsing cake json");
            JsonParser parser = new JsonFactory().createParser(buffer.toString());
            if (JsonToken.START_ARRAY != parser.nextToken()) {
                throw new IOException("bad token");
            }

            JsonToken nextToken = parser.nextToken();
            while(nextToken == JsonToken.START_OBJECT) {
                System.out.println("creating cake entity");

                Cake cakeEntity = new Cake();
                System.out.println(parser.nextFieldName());
                cakeEntity.setTitle(parser.nextTextValue());

                System.out.println(parser.nextFieldName());
                cakeEntity.setDesc(parser.nextTextValue());

                System.out.println(parser.nextFieldName());
                cakeEntity.setImage(parser.nextTextValue());

                daoService.add(cakeEntity);


                Cake savedCake = daoService.getCake(cakeEntity.getId());

                if (savedCake != null){
                    cakes.add(savedCake);//unsaved cakes are not displayed
                }


                nextToken = parser.nextToken();
                System.out.println(nextToken);

                nextToken = parser.nextToken();
                System.out.println(nextToken);
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("init finished");
        return cakes;
    }

    @PostMapping("/cakes")
    public Cake addCake(@RequestBody Cake cakeEntity){
        daoService.add(cakeEntity);
        return cakeEntity;
    }
}
