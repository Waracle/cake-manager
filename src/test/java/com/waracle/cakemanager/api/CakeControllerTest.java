package com.waracle.cakemanager.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemanager.entity.Cake;
import com.waracle.cakemanager.exceptions.NotFoundException;
import com.waracle.cakemanager.service.CakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CakeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private CakeService cakeService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @WithMockUser
    void getCakes_returnsOk() throws Exception {
        Cake cake1 = createTestCake();

        Cake cake2 = new Cake();
        cake2.setId(2L);
        cake2.setName("Strawberry Cake");
        cake2.setDescription("A delicious strawberry cake");

        when(cakeService.getAllCakes()).thenReturn(Arrays.asList(cake1, cake2));

        mockMvc.perform(MockMvcRequestBuilders.get("/cakes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(cake1.getName()))
                .andExpect(jsonPath("$[0].description").value(cake1.getDescription()))
                .andExpect(jsonPath("$[1].name").value(cake2.getName()))
                .andExpect(jsonPath("$[1].description").value(cake2.getDescription()));

        verify(cakeService).getAllCakes();
    }

    @Test
    @WithMockUser
    void getCake_returnsOk() throws Exception {
        Cake cake = createTestCake();

        when(cakeService.getCakeById(1L)).thenReturn(cake);

        mockMvc.perform(MockMvcRequestBuilders.get("/cakes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(cake.getName()))
                .andExpect(jsonPath("$.description").value(cake.getDescription()));

        verify(cakeService).getCakeById(1L);
    }

    @Test
    @WithMockUser
    void getCake_returnsNotFound() throws Exception {
        when(cakeService.getCakeById(1L)).thenThrow(new NotFoundException("Cake not found with id 1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/cakes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cake not found with id 1"));

        verify(cakeService, times(1)).getCakeById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addCake_returnsOk() throws Exception {
        Cake cake = createTestCake();

        when(cakeService.addCake(any(Cake.class))).thenReturn(cake);

        mockMvc.perform(MockMvcRequestBuilders.post("/cakes").with(csrf())
                        .content(objectMapper.writeValueAsString(cake))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(cake.getName()))
                .andExpect(jsonPath("$.description").value(cake.getDescription()));

        verify(cakeService).addCake(any(Cake.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCake_returnsOk() throws Exception {
        Cake cake = createTestCake();

        when(cakeService.updateCake(eq(1L), any(Cake.class))).thenReturn(cake);

        mockMvc.perform(MockMvcRequestBuilders.put("/cakes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cake))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cake.getId()))
                .andExpect(jsonPath("$.name").value(cake.getName()))
                .andExpect(jsonPath("$.description").value(cake.getDescription()));

        ArgumentCaptor<Cake> argumentCaptor = ArgumentCaptor.forClass(Cake.class);
        verify(cakeService, times(1)).updateCake(eq(1L), argumentCaptor.capture());
        assertEquals(1L, argumentCaptor.getValue().getId());
        assertEquals(cake.getName(), argumentCaptor.getValue().getName());
        assertEquals(cake.getDescription(), argumentCaptor.getValue().getDescription());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCake_returnsNotFound() throws Exception {
        Cake cake = createTestCake();
        when(cakeService.updateCake(eq(1L), any(Cake.class))).thenThrow(new NotFoundException("Cake not found with id 1"));

        mockMvc.perform(MockMvcRequestBuilders.put("/cakes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cake))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cake not found with id 1"));

        verify(cakeService, times(0)).deleteCake(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCake_returnsOk() throws Exception {
        doNothing().when(cakeService).deleteCake(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cakes/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(cakeService, times(1)).deleteCake(1L);
    }

    @Test
    @WithMockUser
    void deleteCake_returnsForbidden() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/cakes/1"))
                .andExpect(status().isForbidden());

        verify(cakeService, times(0)).deleteCake(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCake_returnsNotFound() throws Exception {
        doThrow(new NotFoundException("Cake not found with id 1")).when(cakeService).deleteCake(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cakes/1").with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cake not found with id 1"));

        verify(cakeService, times(1)).deleteCake(1L);
    }


    private Cake createTestCake() {
        Cake cake = new Cake();
        cake.setId(1L);
        cake.setName("Chocolate Cake");
        cake.setDescription("A delicious chocolate cake");

        return cake;
    }
}
