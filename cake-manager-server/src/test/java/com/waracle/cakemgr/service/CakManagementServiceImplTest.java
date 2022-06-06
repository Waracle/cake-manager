package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.exception.InvalidCakeException;
import com.waracle.cakemgr.repository.CakeRepository;
import com.waracle.manager.cake.model.Cake;
import com.waracle.manager.cake.model.CakeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CakManagementServiceImplTest {

    @Mock
    private CakeRepository cakeRepository;

    @Mock
    private FileStorageService fileService;

    @InjectMocks
    private CakManagementServiceImpl service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addCakeForValidImageContentTypeTest() {

        //Given
        MultipartFile image = new MockMultipartFile("name","filename","image/png", new byte[] {});
        String title = "title";
        String desc = "desc";

        when(fileService.save(eq(image))).thenReturn(Path.of("test"));

        when(cakeRepository.save(any())).thenReturn(CakeEntity.builder().id(1L).description(desc).title(title).build());

        //When
        CakeResponse response = service.addCake(title, desc, image);


        //Then
        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Test
    void addCakeForInValidImageContentTypeTest() {

        //Given
        MultipartFile image = new MockMultipartFile("name","filename","image/txs", new byte[] {});
        String title = "title";
        String desc = "desc";


        //When
        Exception exception = assertThrows(InvalidCakeException.class, () -> service.addCake(title, desc, image));


        //Then
        String expectedMessage = "Incorrect Content type. Valid Content types are image/jpeg, image/png";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getCakes()  {
        String title = "title";
        String desc = "desc";
        when(cakeRepository.findAll()).thenReturn(List.of(CakeEntity.builder().id(1L).description(desc).title(title).imageUrl("url").build()));


        //When
        List<Cake> response = service.getCakes();

        //Then
        assertNotNull(response);
        assertEquals(1, response.size());
    }
}