package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.entity.Cake;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.service.DaoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CakeController {
    private static final Logger log = LoggerFactory.getLogger(CakeController.class);

    @Autowired
    private DaoService daoService;

    @Autowired
    private CakeService cakeService;


    @GetMapping("/")
    public List<Cake> getSavedCakes() {
        return daoService.getAllCakes();

    }

    @GetMapping("/cakes")
    public Set<Cake> downloadCakes() {
        log.debug("downloading cake json");
        try (InputStream inputStream = new URL("https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json").openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }
            log.debug("parsing cake json");
            return cakeService.parseCake(buffer);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    @PostMapping("/cakes")
    public Cake addCake(@RequestBody Cake cakeEntity) {
        daoService.add(cakeEntity);
        return cakeEntity;
    }


}
