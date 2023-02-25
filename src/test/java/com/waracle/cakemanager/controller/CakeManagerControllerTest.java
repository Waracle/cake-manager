package com.waracle.cakemanager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemanager.entity.CakeEntity;
import com.waracle.cakemanager.service.CakeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CakeManagerControllerTest {

    @MockBean
    private CakeService cakeService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCakeWhenDataAvailableInSystem_Success() throws Exception {
        List<CakeEntity> cakeList = Arrays.asList(getVanillaCake(), getPlumCake());
        when(cakeService.getAllCakes()).thenReturn(cakeList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cakes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getCakeBasedOnCakeId_ThenSuccessResponse() throws Exception {
        when(cakeService.getCakeById(1L)).thenReturn(getPlumCake());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cakes/cake/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasKey("cakeId")))
                .andExpect(jsonPath("$", hasValue(1)))
                .andExpect(jsonPath("$.title").value("Plum Cake"));
    }

    @Test
    public void whenPostRequestToCakeManager_thenSuccessResponse() throws Exception {
        String file_path = "src/main/resources/input/cake_sample.json";
        File file = new File(file_path);
        ObjectMapper mapper = new ObjectMapper();
        CakeEntity cakeEntity = mapper.readValue(file, new TypeReference<CakeEntity>() {
        });
        String cake = mapper.writeValueAsString(cakeEntity);
        when(cakeService.saveCake(Mockito.any(CakeEntity.class))).thenReturn(cakeEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cakes")
                        .content(cake)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.cakeId").value(1))
                .andExpect(jsonPath("$.title").value("Plum Cake"))
                .andExpect(jsonPath("$.description").value("Christmas cake"))
                .andExpect(jsonPath("$.image").value("image2"));
    }

    @Test
    public void whenDeleteRequestToCakeManager_thenSuccessResponse() throws Exception {
        String file_path = "src/main/resources/input/cake_sample.json";
        File file = new File(file_path);
        ObjectMapper mapper = new ObjectMapper();
        CakeEntity cakeEntity = mapper.readValue(file, new TypeReference<CakeEntity>() {
        });
        String cake = mapper.writeValueAsString(cakeEntity);
        when(cakeService.deleteCakeById(Mockito.any())).thenReturn(cakeEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cakes/cake/1")
                        .content(cake)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.cakeId").value(1))
                .andExpect(jsonPath("$.title").value("Plum Cake"));
    }
    @Test
    public void whenUpdateRequestToCakeManager_thenSuccessResponse() throws Exception {
        String file_path = "src/main/resources/input/cake_sample.json";
        File file = new File(file_path);
        ObjectMapper mapper = new ObjectMapper();
        CakeEntity cakeEntity = mapper.readValue(file, new TypeReference<CakeEntity>(){});
        cakeEntity.setTitle("Chocolate Cake");
        String cake = mapper.writeValueAsString(cakeEntity);
        when(cakeService.updateCake(Mockito.anyLong(), Mockito.any(CakeEntity.class))).thenReturn(cakeEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cakes")
                        .content(cake)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.cakeId").value(1))
                .andExpect(jsonPath("$.title").value("Chocolate Cake"));
    }
    @Test
    public void whenUpdateRequestToCakeManagerByCakeId_thenSuccessResponse() throws Exception {
        String file_path = "src/main/resources/input/cake_sample.json";
        File file = new File(file_path);
        ObjectMapper mapper = new ObjectMapper();
        CakeEntity cakeEntity = mapper.readValue(file, new TypeReference<CakeEntity>(){});
        cakeEntity.setTitle("Chocolate Cake");
        String cake = mapper.writeValueAsString(cakeEntity);
        when(cakeService.updateCake(Mockito.anyLong(), Mockito.any(CakeEntity.class))).thenReturn(cakeEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cakes/cake/1")
                        .content(cake)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.cakeId").value(1))
                .andExpect(jsonPath("$.title").value("Chocolate Cake"));
    }

    private CakeEntity getPlumCake() {
        return CakeEntity.builder()
                .cakeId(1L).title("Plum Cake").description("Christmas cake").image("image1")
                .build();
    }

    private CakeEntity getVanillaCake() {
        return CakeEntity.builder()
                .cakeId(2L).title("Vanilla Cake").description("Vanilla cake").image("image2")
                .build();
    }
}