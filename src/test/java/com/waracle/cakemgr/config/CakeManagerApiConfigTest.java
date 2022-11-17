package com.waracle.cakemgr.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CakeManagerApiConfigTest {
    @InjectMocks
    private CakeManagerApiConfig cakeManagerApiConfig;

    @DisplayName("Verify Swagger docket is created with out any exception")
    @Test
    void validateBeanCreation(){
       Docket docket =  cakeManagerApiConfig.swaggerApi();
        assertNotNull(docket);
    }
}
