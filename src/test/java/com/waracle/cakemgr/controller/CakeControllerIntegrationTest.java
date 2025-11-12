package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.service.CakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CakeController.class)
@DisplayName("CakeController Integration Tests")
class CakeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CakeService cakeService;

    private CakeDto cakeDto1;
    private CakeDto cakeDto2;
    private List<CakeDto> cakeList;

    @BeforeEach
    void setUp() {
        cakeDto1 = new CakeDto(1L, "Lemon cheesecake", "A cheesecake made of lemon", "https://example.com/lemon-cheesecake.jpg");
        cakeDto2 = new CakeDto(2L, "Victoria sponge", "A classic British sponge cake", "https://example.com/victoria-sponge.jpg");
        cakeList = Arrays.asList(cakeDto1, cakeDto2);
    }

    @Test
    @DisplayName("GET /cakes - Should return all cakes")
    void getAllCakes_ShouldReturnListOfCakes() throws Exception {
        // Given
        when(cakeService.getAllCakes()).thenReturn(cakeList);

        // When & Then
        mockMvc.perform(get("/cakes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Lemon cheesecake"))
                .andExpect(jsonPath("$[0].description").value("A cheesecake made of lemon"))
                .andExpect(jsonPath("$[0].image").value("https://example.com/lemon-cheesecake.jpg"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Victoria sponge"))
                .andExpect(jsonPath("$[1].description").value("A classic British sponge cake"))
                .andExpect(jsonPath("$[1].image").value("https://example.com/victoria-sponge.jpg"));

        verify(cakeService, times(1)).getAllCakes();
    }

    @Test
    @DisplayName("GET /cakes - Should return empty list when no cakes exist")
    void getAllCakes_ShouldReturnEmptyList_WhenNoCakesExist() throws Exception {
        // Given
        when(cakeService.getAllCakes()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/cakes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(cakeService, times(1)).getAllCakes();
    }

    @Test
    @DisplayName("POST /cakes - Should create a new cake")
    void createCake_ShouldReturnCreatedCake() throws Exception {
        // Given
        CakeDto newCake = new CakeDto(null, "Carrot cake", "A cake made with carrots", "https://example.com/carrot-cake.jpg");
        CakeDto savedCake = new CakeDto(3L, "Carrot cake", "A cake made with carrots", "https://example.com/carrot-cake.jpg");
        
        when(cakeService.createCake(any(CakeDto.class))).thenReturn(savedCake);

        // When & Then
        mockMvc.perform(post("/cakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCake)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("Carrot cake"))
                .andExpect(jsonPath("$.description").value("A cake made with carrots"))
                .andExpect(jsonPath("$.image").value("https://example.com/carrot-cake.jpg"));

        verify(cakeService, times(1)).createCake(any(CakeDto.class));
    }

    @Test
    @DisplayName("POST /cakes - Should handle invalid request body")
    void createCake_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
        // Given - invalid JSON
        String invalidJson = "{\"title\": \"Invalid Cake\", \"description\": }";

        // When & Then
        mockMvc.perform(post("/cakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(cakeService, never()).createCake(any(CakeDto.class));
    }

    @Test
    @DisplayName("PUT /cakes/{id} - Should update existing cake")
    void updateCake_ShouldReturnUpdatedCake() throws Exception {
        // Given
        Long cakeId = 1L;
        CakeDto updateRequest = new CakeDto(null, "Updated Lemon cheesecake", "An updated description", "https://example.com/updated-lemon.jpg");
        CakeDto updatedCake = new CakeDto(cakeId, "Updated Lemon cheesecake", "An updated description", "https://example.com/updated-lemon.jpg");
        
        when(cakeService.updateCake(eq(cakeId), any(CakeDto.class))).thenReturn(updatedCake);

        // When & Then
        mockMvc.perform(put("/cakes/{id}", cakeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Lemon cheesecake"))
                .andExpect(jsonPath("$.description").value("An updated description"))
                .andExpect(jsonPath("$.image").value("https://example.com/updated-lemon.jpg"));

        verify(cakeService, times(1)).updateCake(eq(cakeId), any(CakeDto.class));
    }

    @Test
    @DisplayName("PUT /cakes/{id} - Should return 404 when cake not found")
    void updateCake_ShouldReturnNotFound_WhenCakeDoesNotExist() throws Exception {
        // Given
        Long nonExistentId = 999L;
        CakeDto updateRequest = new CakeDto(null, "Updated Cake", "Updated description", "https://example.com/updated.jpg");
        
        when(cakeService.updateCake(eq(nonExistentId), any(CakeDto.class)))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Cake not found with id: " + nonExistentId));

        // When & Then
        mockMvc.perform(put("/cakes/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(cakeService, times(1)).updateCake(eq(nonExistentId), any(CakeDto.class));
    }

    @Test
    @DisplayName("PUT /cakes/{id} - Should handle invalid request body")
    void updateCake_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
        // Given
        Long cakeId = 1L;
        String invalidJson = "{\"title\": \"Invalid\", \"description\": }";

        // When & Then
        mockMvc.perform(put("/cakes/{id}", cakeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(cakeService, never()).updateCake(eq(cakeId), any(CakeDto.class));
    }

    @Test
    @DisplayName("DELETE /cakes/{id} - Should delete cake successfully")
    void deleteCake_ShouldReturnNoContent() throws Exception {
        // Given
        Long cakeId = 1L;
        doNothing().when(cakeService).deleteCake(cakeId);

        // When & Then
        mockMvc.perform(delete("/cakes/{id}", cakeId))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(cakeService, times(1)).deleteCake(cakeId);
    }

    @Test
    @DisplayName("DELETE /cakes/{id} - Should return 404 when cake not found")
    void deleteCake_ShouldReturnNotFound_WhenCakeDoesNotExist() throws Exception {
        // Given
        Long nonExistentId = 999L;
        doThrow(new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Cake not found with id: " + nonExistentId))
                .when(cakeService).deleteCake(nonExistentId);

        // When & Then
        mockMvc.perform(delete("/cakes/{id}", nonExistentId))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(cakeService, times(1)).deleteCake(nonExistentId);
    }

    @Test
    @DisplayName("GET /cakes - Should handle service exception gracefully")
    void getAllCakes_ShouldHandleServiceException() throws Exception {
        // Given
        when(cakeService.getAllCakes()).thenThrow(new RuntimeException("Database connection error"));

        // When & Then
        mockMvc.perform(get("/cakes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError());

        verify(cakeService, times(1)).getAllCakes();
    }

    
}
