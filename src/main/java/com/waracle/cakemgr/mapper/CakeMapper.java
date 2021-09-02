package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.dao.CakeEntity;
import com.waracle.cakemgr.dto.CakeRequestDto;
import com.waracle.cakemgr.dto.CakeResponseDto;
import org.modelmapper.ModelMapper;

public class CakeMapper {

  private static final ModelMapper modelMapper = new ModelMapper();

  private CakeMapper() {
  }

  public static CakeResponseDto fromEntityToResponseDto(CakeEntity cakeEntity) {
    modelMapper.typeMap(CakeEntity.class, CakeResponseDto.class)
        .addMapping(CakeEntity::getTitle, CakeResponseDto::setName)
        .addMapping(CakeEntity::getDesc, CakeResponseDto::setDescription)
        .addMapping(CakeEntity::getImage, CakeResponseDto::setImageUrl);

    return modelMapper.map(cakeEntity, CakeResponseDto.class);
  }

  public static CakeEntity fromRequestDtoToEntity(CakeRequestDto cakeDto) {
    modelMapper.typeMap(CakeRequestDto.class, CakeEntity.class)
        .addMapping(CakeRequestDto::getName, CakeEntity::setTitle)
        .addMapping(CakeRequestDto::getDescription, CakeEntity::setDesc)
        .addMapping(CakeRequestDto::getImageUrl, CakeEntity::setImage);

    return modelMapper.map(cakeDto, CakeEntity.class);
  }

}
