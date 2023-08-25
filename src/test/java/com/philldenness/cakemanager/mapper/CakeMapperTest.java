package com.philldenness.cakemanager.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.entity.CakeEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CakeMapperTest {

	@InjectMocks
	private CakeMapper cakeMapper;

	@Test
	void shouldConvertEntityToDTO() {
		CakeEntity cakeEntity = new CakeEntity();
		cakeEntity.setTitle("a title");
		cakeEntity.setDescription("a description");
		cakeEntity.setImage("an image");

		CakeDTO cakeDTO = cakeMapper.toDTO(cakeEntity);

		assertEquals(cakeEntity.getTitle(), cakeDTO.title());
		assertEquals(cakeEntity.getDescription(), cakeDTO.description());
		assertEquals(cakeEntity.getImage(), cakeDTO.image());
	}
}