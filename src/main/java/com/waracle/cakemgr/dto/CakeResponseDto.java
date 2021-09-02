package com.waracle.cakemgr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CakeResponseDto {

  private String name;
  private String description;
  private String imageUrl;
}
