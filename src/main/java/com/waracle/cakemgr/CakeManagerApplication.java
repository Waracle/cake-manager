package com.waracle.cakemgr;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CakeManagerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(CakeManagerApplication.class)
                .run(args);
    }
}
