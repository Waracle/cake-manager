package com.waracle.cakemgr.util;

import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.entity.CakeEntity;
import org.springframework.stereotype.Component;

@Component
public class CakeMapper {

    public CakeDto toDto(CakeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CakeDto(
            entity.getId(),
            entity.getTitle(),
            entity.getDescription(),
            entity.getImage()
        );
    }

    public CakeEntity toEntity(CakeDto dto) {
        if (dto == null) {
            return null;
        }
        CakeEntity entity = new CakeEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setImage(dto.getImage());
        return entity;
    }
}
