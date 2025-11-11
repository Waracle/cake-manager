package com.waracle.cakemgr.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cakeManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cake Manager API")
                        .description("REST API for managing cakes")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Waracle")
                                .url("https://waracle.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8282")
                                .description("Local Development Server")
                ));
    }
}
