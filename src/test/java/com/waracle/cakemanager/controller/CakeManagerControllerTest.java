package com.waracle.cakemanager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemanager.entity.CakeEntity;
import com.waracle.cakemanager.service.CakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
class CakeManagerControllerTest {

    @MockBean
    private CakeService cakeService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @WithMockUser
    public void getCakeWhenDataAvailableInSystemAsUser_Success() throws Exception {
        List<CakeEntity> cakeList = Arrays.asList(getVanillaCake(), getPlumCake());
        when(cakeService.getAllCakes()).thenReturn(cakeList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cakes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithAnonymousUser
    public void getCakeWithAnonymusUser_ReturnUnAuthorized() throws Exception {
        List<CakeEntity> cakeList = Arrays.asList(getVanillaCake(), getPlumCake());
        when(cakeService.getAllCakes()).thenReturn(cakeList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cakes"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    @WithMockUser
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
    @WithMockUser(roles = "ADMIN_ROLE")
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
                        .with(csrf())
                        .content(cake)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.cakeId").value(1))
                .andExpect(jsonPath("$.title").value("Plum Cake"))
                .andExpect(jsonPath("$.description").value("Cake rich with nuts and dried fruits"))
                .andExpect(jsonPath("$.image").value("https://www.dialabouquet.in/wp-content/uploads/2014/04/plum-cake.jpg"));
    }

    @Test
    @WithMockUser(value = "admin")
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
                        .with(csrf())
                        .content(cake)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.cakeId").value(1))
                .andExpect(jsonPath("$.title").value("Plum Cake"));
    }

    @Test
    @WithMockUser(roles = "ADMIN_ROLE")
    public void whenUpdateRequestToCakeManager_thenSuccessResponse() throws Exception {
        String file_path = "src/main/resources/input/cake_sample.json";
        File file = new File(file_path);
        ObjectMapper mapper = new ObjectMapper();
        CakeEntity cakeEntity = mapper.readValue(file, new TypeReference<CakeEntity>() {
        });
        cakeEntity.setTitle("Chocolate Cake");
        String cake = mapper.writeValueAsString(cakeEntity);
        when(cakeService.updateCake(Mockito.anyLong(), Mockito.any(CakeEntity.class))).thenReturn(cakeEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cakes")
                        .with(csrf())
                        .content(cake)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.cakeId").value(1))
                .andExpect(jsonPath("$.title").value("Chocolate Cake"));
    }

    @Test
    @WithMockUser(roles = "ADMIN_ROLE")
    public void whenUpdateRequestToCakeManagerByCakeId_thenSuccessResponse() throws Exception {
        String file_path = "src/main/resources/input/cake_sample.json";
        File file = new File(file_path);
        ObjectMapper mapper = new ObjectMapper();
        CakeEntity cakeEntity = mapper.readValue(file, new TypeReference<CakeEntity>() {
        });
        cakeEntity.setTitle("Chocolate Cake");
        String cake = mapper.writeValueAsString(cakeEntity);
        when(cakeService.updateCake(Mockito.anyLong(), Mockito.any(CakeEntity.class))).thenReturn(cakeEntity);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cakes/cake/1")
                        .with(csrf())
                        .param("action", "signup")
                        .content(cake)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.cakeId").value(1))
                .andExpect(jsonPath("$.title").value("Chocolate Cake"));
    }

    private CakeEntity getPlumCake() {
        return CakeEntity.builder()
                .cakeId(1L).title("Plum Cake").description("Cake rich with nuts and dried fruits").image("image1")
                .build();
    }

    private CakeEntity getVanillaCake() {
        return CakeEntity.builder()
                .cakeId(2L).title("Vanilla Cake").description("Vanilla cake").image("image2")
                .build();
    }
}