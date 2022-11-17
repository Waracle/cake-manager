package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.model.CakeSaveResponse;
import com.waracle.cakemgr.service.CakeManagerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CakeManagerController.class}, properties = {"spring.main.allow-bean-definition-overriding=true"})
@ComponentScan(basePackages = {"com.waracle.cakemgr"})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CakeManagerControllerTest {

    @InjectMocks
    CakeManagerController  cakeManagerController;

    @Autowired
    MockMvc mvc;

    @Mock
    CakeManagerService cakeManagerService;

    private HttpHeaders httpHeaders;


    @Test
    void validateAPIResponse() throws Exception {
        when(cakeManagerService.getCakes()).thenReturn(Collections.singletonList(new CakeEntity()));
        mvc = MockMvcBuilders.standaloneSetup(cakeManagerController).build();
        MockHttpServletRequestBuilder builder = get("/cakes");
       ResultActions resultActions =  mvc.perform(builder);
        resultActions.andExpect(status().isOk());
        assertNotNull(resultActions.andReturn());

    }

    @DisplayName("verify API request without post request body should return bad request")
    @Test
    void validateAPIPostResponseIsBadRequest() throws Exception {
        CakeSaveResponse cakeSave= new CakeSaveResponse();
        ResponseEntity<CakeSaveResponse> cakeSaveResponse = new ResponseEntity(cakeSave, new HttpHeaders(),HttpStatus.OK);

        when(cakeManagerService.saveCakes(any(CakeEntity.class))).thenReturn( cakeSaveResponse);
        mvc = MockMvcBuilders.standaloneSetup(cakeManagerController).build();
        MockHttpServletRequestBuilder builder = post("/cakes");
        ResultActions resultActions =  mvc.perform(builder);
        resultActions.andExpect(status().isBadRequest());
    }

    @DisplayName("verify API request without post request body should return bad request")
    @Test
    void validateAPIPostResponseIsValid() throws Exception {
        CakeSaveResponse cakeSave= new CakeSaveResponse();
        ResponseEntity<CakeSaveResponse> cakeSaveResponse = new ResponseEntity(cakeSave, new HttpHeaders(),HttpStatus.OK);

        when(cakeManagerService.saveCakes(any(CakeEntity.class))).thenReturn( cakeSaveResponse);
        mvc = MockMvcBuilders.standaloneSetup(cakeManagerController).build();
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setDescription("test");
        cakeEntity.setTitle("new test cake");
        MockHttpServletRequestBuilder builder = post("/cakes")
        .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(cakeEntity));
        ResultActions resultActions =  mvc.perform(builder);
        resultActions.andExpect(status().isOk());
    }


}
