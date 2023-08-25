package com.philldenness.cakemanager.mapper;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.entity.CakeEntity;
import org.springframework.stereotype.Component;

@Component
public class CakeMapper {
	public CakeDTO toDTO(CakeEntity entity) {
		return new CakeDTO(entity.getTitle(), entity.getDescription(), entity.getImage());
	}
}
