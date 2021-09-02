package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.dao.CakeEntity;
import com.waracle.cakemgr.dto.CakeRequestDto;
import com.waracle.cakemgr.dto.CakeResponseDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CakeMapperTest {

  @Test
  void testMapFromEntityToResponseDto() {
    CakeEntity cakeEntity = new CakeEntity();
    cakeEntity.setTitle("Cake Title");
    cakeEntity.setDesc("Cake Desc123");
    cakeEntity.setImage("Cake Img Url");

    CakeResponseDto expectedResponseDto = new CakeResponseDto();
    expectedResponseDto.setName("Cake Title");
    expectedResponseDto.setDescription("Cake Desc123");
    expectedResponseDto.setImageUrl("Cake Img Url");

    CakeResponseDto actualResponseDto = CakeMapper.fromEntityToResponseDto(cakeEntity);

    assertThat(actualResponseDto).isEqualTo(expectedResponseDto);
  }

  @Test
  void testMapFromRequestDtoToEntity() {
    CakeRequestDto cakeRequestDto = new CakeRequestDto();
    cakeRequestDto.setName("Cake Title");
    cakeRequestDto.setDescription("Cake Desc123");
    cakeRequestDto.setImageUrl("Cake Img Url");

    CakeEntity expectedCakeEntity = new CakeEntity();
    expectedCakeEntity.setTitle("Cake Title");
    expectedCakeEntity.setDesc("Cake Desc123");
    expectedCakeEntity.setImage("Cake Img Url");

    CakeEntity actualEntity = CakeMapper.fromRequestDtoToEntity(cakeRequestDto);

    assertThat(actualEntity).isEqualTo(expectedCakeEntity);
  }

}
