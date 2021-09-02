package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dao.CakeEntity;
import com.waracle.cakemgr.dto.CakeRequestDto;
import com.waracle.cakemgr.dto.CakeResponseDto;
import com.waracle.cakemgr.service.CakeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CakeControllerTest {

  @Mock
  private CakeService cakeService;

  @InjectMocks
  private CakeController cakeController;

  @Test
  void testGetReturnsListOfCakes() {
    CakeEntity cakeEntity1 = new CakeEntity();
    cakeEntity1.setTitle("entity1Title123");
    cakeEntity1.setDesc("entity1Description123");
    cakeEntity1.setImage("entity1ImageUrl123");

    CakeResponseDto cakeResponse1 = new CakeResponseDto();
    cakeResponse1.setName("entity1Title123");
    cakeResponse1.setDescription("entity1Description123");
    cakeResponse1.setImageUrl("entity1ImageUrl123");

    CakeEntity cakeEntity2 = new CakeEntity();
    cakeEntity2.setTitle("entity2Title123");
    cakeEntity2.setDesc("entity2Description123");
    cakeEntity2.setImage("entity2ImageUrl123");

    CakeResponseDto cakeResponse2 = new CakeResponseDto();
    cakeResponse2.setName("entity2Title123");
    cakeResponse2.setDescription("entity2Description123");
    cakeResponse2.setImageUrl("entity2ImageUrl123");

    List<CakeEntity> cakeEntityList = new ArrayList<>();
    cakeEntityList.add(cakeEntity1);
    cakeEntityList.add(cakeEntity2);

    List<CakeResponseDto> expectedCakeResponse = new ArrayList<>();
    expectedCakeResponse.add(cakeResponse1);
    expectedCakeResponse.add(cakeResponse2);

    when(cakeService.getCakes()).thenReturn(cakeEntityList);

    ResponseEntity<List<CakeResponseDto>> response = cakeController.getCakes();

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(expectedCakeResponse);
  }

  @Test
  void testPostSucceedsWithOkResponse() {
    CakeRequestDto cakeRequestDto = new CakeRequestDto();
    cakeRequestDto.setName("testTitle123");
    cakeRequestDto.setDescription("testDescription123");
    cakeRequestDto.setImageUrl("testImageUrl123");

    ResponseEntity<String> response = cakeController.postCake(cakeRequestDto);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    verify(cakeService, times(1)).writeCake(any(CakeEntity.class));
  }
}
