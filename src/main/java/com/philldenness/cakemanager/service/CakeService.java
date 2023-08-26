package com.philldenness.cakemanager.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.dto.CakeRequest;
import com.philldenness.cakemanager.entity.CakeEntity;
import com.philldenness.cakemanager.mapper.CakeMapper;
import com.philldenness.cakemanager.repository.CakeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CakeService {
	private final CakeRepository repository;
	private final CakeMapper mapper;

	public List<CakeDTO> getCakes() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public CakeDTO getCakeById(Long id) {
		CakeEntity cakeEntity = repository.findById(id).orElseThrow(() -> {
			log.warn("Unknown cake ID", keyValue("cakeId", id));
			return new IllegalArgumentException();
		});
		return mapper.toDTO(cakeEntity);
	}

	public CakeDTO create(CakeRequest toSave) {
		CakeEntity cakeEntity = mapper.toEntity(toSave);
		CakeEntity savedEntity = repository.save(cakeEntity);
		return mapper.toDTO(savedEntity);
	}
}
