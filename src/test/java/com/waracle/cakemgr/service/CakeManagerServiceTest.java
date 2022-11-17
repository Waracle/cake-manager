package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.exception.CakeManagerException;
import com.waracle.cakemgr.model.CakeSaveResponse;
import com.waracle.cakemgr.repository.CakeManagerRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CakeManagerServiceTest {

    @InjectMocks
   private CakeManagerService cakeManagerService;
   @Mock
    CakeManagerRepository cakeManagerRepository;

    @BeforeEach
    void init(){
        //cakeManagerService = new CakeManagerService();
    }

    @DisplayName("Test 400 bad request and response is thrown")
    @ParameterizedTest
    @NullSource
    void validateResponseWhenInputIsNull(CakeEntity cakeEntity){
        ResponseEntity<CakeSaveResponse> response = cakeManagerService.saveCakes(cakeEntity);
        assertEquals("400 BAD_REQUEST", response.getStatusCode().toString());
    }


    @ParameterizedTest
    @MethodSource("apiRequestSource")
    void validateSuccessResponse(CakeEntity cakeEntity) {
        //Mockito.when(cakeManagerRepository.save(any(CakeEntity.class))).thenReturn(cakeEntity);
        ResponseEntity<CakeSaveResponse> response = cakeManagerService.saveCakes(cakeEntity);
        assertEquals("200 OK", response.getStatusCode().toString());

    }

    @Test
    void validateException(){
        Mockito.when(cakeManagerRepository.save(any(CakeEntity.class))).thenThrow(IllegalArgumentException.class);
        CakeEntity cakeEntity = new CakeEntity();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                cakeManagerService.saveCakes(cakeEntity));
        assertTrue(exception instanceof IllegalArgumentException );
    }

    @DisplayName("verify the result returned.")
    @ParameterizedTest
    @MethodSource("supplyGetResults")
    void validateGetResult(List<CakeEntity> expectedResult){
        Mockito.when(cakeManagerRepository.findAll()).thenReturn(expectedResult);
       List<CakeEntity> resultList = cakeManagerService.getCakes();
       assertEquals(expectedResult.size(), resultList.size());
    }

    @DisplayName("verify the result returned when no entries in DB")
    @Test
    void validateGetResultWhenNull(){
        Mockito.when(cakeManagerRepository.findAll()).thenReturn(null);
        List<CakeEntity> resultList = cakeManagerService.getCakes();
        assertEquals(0, resultList.size());
    }

    @DisplayName("Verify constraint violation exception thrown while saving data to DB")
    @Test
    void validateExceptionWhenWritingDatatoDB(){
        Mockito.when(cakeManagerRepository.save(any(CakeEntity.class))).thenThrow(ConstraintViolationException.class);
        CakeManagerException exception = assertThrows(CakeManagerException.class, () ->
                cakeManagerService.initialize());
        assertTrue(exception instanceof CakeManagerException );
    }



    @DisplayName("Verify result set returned without any exception")
    @Test
    void validateInitialDataLoadWithOutException() throws Exception {
        cakeManagerService.initialize();
        List<CakeEntity> cakeEntityList = cakeManagerService.getCakes();
        assertNotNull(cakeEntityList);
    }


    private static Stream<CakeEntity> apiRequestSource (){
        CakeEntity cakeEntity1 = new CakeEntity();
        cakeEntity1.setImage("http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg");
        cakeEntity1.setTitle("new cake 1");
        cakeEntity1.setDescription("Bugs bunnys favourite");
        return Stream.of(cakeEntity1);
    }

    private static Stream<List<CakeEntity>> supplyGetResults(){
        List<CakeEntity> list = new ArrayList<>();
        CakeEntity cakeEntity1 = new CakeEntity();
        cakeEntity1.setImage("http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg");
        cakeEntity1.setTitle("new cake 1");
        cakeEntity1.setDescription("Bugs bunnys favourite");
        list.add(cakeEntity1);
        return Stream.of(new ArrayList<>(), list);
    }

}
