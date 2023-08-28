package com.philldenness.cakemanager.mapper;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.dto.CakeRequest;
import com.philldenness.cakemanager.entity.CakeEntity;
import org.springframework.stereotype.Component;

@Component
public class CakeMapper {
	public CakeDTO toDTO(CakeEntity entity) {
		return new CakeDTO(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getImage());
	}

	public CakeEntity toEntity(CakeRequest cakeRequest) {
		CakeEntity cakeEntity = new CakeEntity();
		cakeEntity.setTitle(cakeRequest.title());
		cakeEntity.setDescription(cakeRequest.description());
		cakeEntity.setImage(cakeRequest.image());

		return cakeEntity;
	}
}
