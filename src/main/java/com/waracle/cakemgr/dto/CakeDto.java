package com.waracle.cakemgr.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Cake data transfer object")
public class CakeDto {

    @Schema(description = "Unique identifier of the cake", example = "1")
    private Long id;
    
    @Schema(description = "Title of the cake", example = "Lemon cheesecake", required = true)
    private String title;
    
    @Schema(description = "Description of the cake", example = "A cheesecake made of lemon", required = true)
    private String description;
    
    @Schema(description = "Image URL of the cake", example = "https://example.com/cake.jpg", required = true)
    private String image;

    public CakeDto() {
    }

    public CakeDto(Long id, String title, String description, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
