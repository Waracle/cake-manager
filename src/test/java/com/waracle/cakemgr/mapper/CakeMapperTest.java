package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.dao.Cake;
import com.waracle.cakemgr.dto.CakeRequestDto;
import com.waracle.cakemgr.dto.CakeResponseDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CakeMapperTest {

  @Test
  void testEntityToResponseDto() {
    Cake cake = new Cake();
    cake.setTitle("Cake Title");
    cake.setDesc("Cake Desc123");
    cake.setImage("Cake Img Url");

    CakeResponseDto expectedResponseDto = new CakeResponseDto();
    expectedResponseDto.setName("Cake Title");
    expectedResponseDto.setDescription("Cake Desc123");
    expectedResponseDto.setImageUrl("Cake Img Url");

    CakeResponseDto actualResponseDto = CakeMapper.fromEntityToResponseDto(cake);

    assertThat(actualResponseDto).isEqualTo(expectedResponseDto);
  }

  @Test
  void testRequestDtoToEntity() {
    CakeRequestDto cakeRequestDto = new CakeRequestDto();
    cakeRequestDto.setName("Cake Title");
    cakeRequestDto.setDescription("Cake Desc123");
    cakeRequestDto.setImageUrl("Cake Img Url");

    Cake expectedCake = new Cake();
    expectedCake.setTitle("Cake Title");
    expectedCake.setDesc("Cake Desc123");
    expectedCake.setImage("Cake Img Url");

    Cake actualEntity = CakeMapper.fromRequestDtoToEntity(cakeRequestDto);

    assertThat(actualEntity).isEqualTo(expectedCake);
  }

}
