package com.philldenness.cakemanager.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.dto.CakeRequest;
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
	void shouldMapEntityToDto() {
		CakeEntity cakeEntity = new CakeEntity();
		cakeEntity.setId(1L);
		cakeEntity.setTitle("a title");
		cakeEntity.setDescription("a description");
		cakeEntity.setImage("an image");

		CakeDTO cakeDTO = cakeMapper.toDTO(cakeEntity);

		assertEquals(cakeEntity.getId(), cakeDTO.id());
		assertEquals(cakeEntity.getTitle(), cakeDTO.title());
		assertEquals(cakeEntity.getDescription(), cakeDTO.description());
		assertEquals(cakeEntity.getImage(), cakeDTO.image());
	}

	@Test
	void shouldMapRequestToEntity() {
		CakeRequest cakeRequest = new CakeRequest( "a title", "a desc", "an image");

		CakeEntity cakeEntity = cakeMapper.toEntity(cakeRequest);

		assertNull(cakeEntity.getId());
		assertEquals(cakeRequest.title(), cakeEntity.getTitle());
		assertEquals(cakeRequest.description(), cakeEntity.getDescription());
		assertEquals(cakeRequest.image(), cakeEntity.getImage());
	}
}