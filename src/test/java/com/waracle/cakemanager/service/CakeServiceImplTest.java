package com.waracle.cakemanager.service;

import com.waracle.cakemanager.entity.CakeEntity;
import com.waracle.cakemanager.exception.CakeAlreadyPresentException;
import com.waracle.cakemanager.exception.CakeNotAvailableException;
import com.waracle.cakemanager.repository.CakeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CakeServiceImplTest {
    @Mock
    CakeRepository cakeRepository;
    @Autowired
    @InjectMocks
    private CakeServiceImpl cakeService;
    private CakeEntity cake;
    List<CakeEntity> cakeEntityList;

    @BeforeEach
    public void setUp() {
        cakeEntityList = new ArrayList<>();
        cake = new CakeEntity(1L, "Vanilla Cake","Vanilla cake","image1");
        cakeEntityList.add(cake);
    }

    @AfterEach
    public void tearDown() {
        cake =  null;
        cakeEntityList = null;
    }

    @Test
    public void GivenGetCakesThenReturnListOfAllCakes(){
        when(cakeRepository.findAll()).thenReturn(cakeEntityList);
        List<CakeEntity> cakeEntityList1 =cakeService.getAllCakes();
        assertEquals(cakeEntityList1,cakeEntityList);
        verify(cakeRepository,times(1)).findAll();
    }

    @Test
    public void GivenGetCakes_NoCakeInSystem_ThenReturnNoCakeAvailableException(){
        cakeEntityList.clear();
        when(cakeRepository.findAll()).thenReturn(cakeEntityList);
        Exception exception = assertThrows(CakeNotAvailableException.class, () -> {
            cakeService.getAllCakes();
        });
        verify(cakeRepository,times(1)).findAll();
        assertTrue(exception instanceof CakeNotAvailableException);
    }

    @Test
    public void GivenGetCakesById_NoCakeInSystem_ThenReturnNoCakeAvailableException(){
        cake= null;
        when(cakeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cake));
        Exception exception = assertThrows(CakeNotAvailableException.class, () -> {
            cakeService.getCakeById(1L);
        });
        assertTrue(exception instanceof CakeNotAvailableException);
    }

    @Test
    void GivenPostCakeThenReturnSavedCake() throws CakeAlreadyPresentException {
        when(cakeRepository.save(any())).thenReturn(cake);
        CakeEntity cakeEntity1 = cakeService.saveCake(cake);
        assertEquals(cakeEntity1,cake);
        verify(cakeRepository,times(1)).save(any());
        verify(cakeRepository,times(1)).existsById(any());
    }

    @Test
    void GivenUpdateCakeByIdThenReturnSavedCake() throws CakeAlreadyPresentException {

        when(cakeRepository.existsById(any())).thenReturn(true);
        when(cakeRepository.save(any())).thenReturn(cake);
        CakeEntity cakeEntity1 = cakeService.updateCake(1L, cake);
        assertEquals(cakeEntity1,cake);
        verify(cakeRepository,times(1)).save(any());
        verify(cakeRepository,times(1)).existsById(any());
    }

    @Test
    void GivenCakeIdThenReturnCakeForThatId() {
        when(cakeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cake));
        CakeEntity cakeEntity1 =cakeService.getCakeById(1L);
        assertEquals(cakeEntity1,cake);
        verify(cakeRepository,times(1)).findById(anyLong());
    }
    @Test
    void GivenDeleteCakeByIdThenReturnCakeForThatId() {
        when(cakeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cake));
        CakeEntity cakeEntity1 =cakeService.deleteCakeById(1L);
        verify(cakeRepository,times(1)).findById(anyLong());
    }
    @Test
    void GivenDeleteCakeById_IdNotPresent_ThenReturnNoCakeAvailableException() {
        cake = null;
        when(cakeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cake));
        Exception exception = assertThrows(CakeNotAvailableException.class, () -> {
            cakeService.deleteCakeById(1L);
        });
        verify(cakeRepository,times(1)).findById(anyLong());
        assertTrue(exception instanceof CakeNotAvailableException);
    }
}