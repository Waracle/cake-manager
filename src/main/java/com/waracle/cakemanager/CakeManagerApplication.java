package com.waracle.cakemanager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class CakeManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakeManagerApplication.class, args);
	}

}
