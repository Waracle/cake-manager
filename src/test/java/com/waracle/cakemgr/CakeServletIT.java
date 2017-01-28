package com.waracle.cakemgr;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CakeServletIT {

    @Test
    public void generatesSyntacticallyValidJson() throws Exception {
        // Given
        URL integrationTestingUrl = new URL("http://localhost:8282/cakes");
        ObjectMapper om = new ObjectMapper();

        // When
        List<CakeEntity> cakes = om.readValue(integrationTestingUrl, new TypeReference<List<CakeEntity>>(){});

        // Then
        assertThat(cakes).hasSize(20);
    }
}
