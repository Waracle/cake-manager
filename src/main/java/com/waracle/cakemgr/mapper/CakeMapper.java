package com.waracle.cakemgr.mapper;

import com.waracle.cakemgr.dao.Cake;
import com.waracle.cakemgr.dto.CakeRequestDto;
import com.waracle.cakemgr.dto.CakeResponseDto;
import org.modelmapper.ModelMapper;

public class CakeMapper {

  private static final ModelMapper modelMapper = new ModelMapper();

  private CakeMapper() {
  }

  public static CakeResponseDto fromEntityToResponseDto(Cake cake) {
    modelMapper.typeMap(Cake.class, CakeResponseDto.class)
        .addMapping(Cake::getTitle, CakeResponseDto::setName)
        .addMapping(Cake::getDesc, CakeResponseDto::setDescription)
        .addMapping(Cake::getImage, CakeResponseDto::setImageUrl);

    return modelMapper.map(cake, CakeResponseDto.class);
  }

  public static Cake fromRequestDtoToEntity(CakeRequestDto cakeDto) {
    modelMapper.typeMap(CakeRequestDto.class, Cake.class)
        .addMapping(CakeRequestDto::getName, Cake::setTitle)
        .addMapping(CakeRequestDto::getDescription, Cake::setDesc)
        .addMapping(CakeRequestDto::getImageUrl, Cake::setImage);

    return modelMapper.map(cakeDto, Cake.class);
  }

}
