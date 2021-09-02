package com.waracle.cakemgr.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CakeRequestDto {

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotBlank
  private String imageUrl;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
