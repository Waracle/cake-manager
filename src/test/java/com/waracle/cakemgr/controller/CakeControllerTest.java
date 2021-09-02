package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dao.Cake;
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
  void testListOfCakes() {
    Cake cake1 = new Cake();
    cake1.setTitle("Cake Title 123");
    cake1.setDesc("Cake desc 123");
    cake1.setImage("Cake Img Url ");

    CakeResponseDto cakeResponse1 = new CakeResponseDto();
    cakeResponse1.setName("Cake Title 123");
    cakeResponse1.setDescription("Cake desc 123");
    cakeResponse1.setImageUrl("Cake Img Url ");

    Cake cake2 = new Cake();
    cake2.setTitle("Cake title 909");
    cake2.setDesc("Cake desc 909");
    cake2.setImage("Cake Img Url 9090");

    CakeResponseDto cakeResponse2 = new CakeResponseDto();
    cakeResponse2.setName("Cake title 909");
    cakeResponse2.setDescription("Cake desc 909");
    cakeResponse2.setImageUrl("Cake Img Url 9090");

    List<Cake> cakeList = new ArrayList<>();
    cakeList.add(cake1);
    cakeList.add(cake2);

    List<CakeResponseDto> expectedCakeResponse = new ArrayList<>();
    expectedCakeResponse.add(cakeResponse1);
    expectedCakeResponse.add(cakeResponse2);

    when(cakeService.getCakes()).thenReturn(cakeList);

    ResponseEntity<List<CakeResponseDto>> response = cakeController.getCakes();

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo(expectedCakeResponse);
  }

  @Test
  void testPostMethod() {
    CakeRequestDto cakeRequestDto = new CakeRequestDto();
    cakeRequestDto.setName("Title_123");
    cakeRequestDto.setDescription("Description_123");
    cakeRequestDto.setImageUrl("ImageUrl_123");

    ResponseEntity<String> response = cakeController.addCake(cakeRequestDto);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    verify(cakeService, times(1)).writeCake(any(Cake.class));
  }
}
