package com.waracle.cakemgr;

import com.waracle.cakemgr.service.CakeManagerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class CakeManagerApplication {


    public static void main(String[] args) throws Exception {
        CakeManagerService service = SpringApplication.run(CakeManagerApplication.class, args).getBean(CakeManagerService.class);
        service.initialize();
    }


}


