package com.philldenness.cakemanager.service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.dto.CakeRequest;
import com.philldenness.cakemanager.entity.CakeEntity;
import com.philldenness.cakemanager.mapper.CakeMapper;
import com.philldenness.cakemanager.metrics.CounterManager;
import com.philldenness.cakemanager.metrics.CounterName;
import com.philldenness.cakemanager.repository.CakeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CakeService {
	private final CakeRepository repository;
	private final CakeMapper mapper;
	private final CounterManager counterManager;

	public List<CakeDTO> getCakes() {
		counterManager.increment(CounterName.FIND_ALL_COUNTER);
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	public CakeDTO getCakeById(Long id) {
		CakeEntity cakeEntity = repository.findById(id).orElseThrow(() -> {
			log.warn("Unknown cake ID", keyValue("cakeId", id));
			return new IllegalArgumentException();
		});
		counterManager.increment(CounterName.FIND_BY_ID_COUNTER);
		return mapper.toDTO(cakeEntity);
	}

	public CakeDTO create(CakeRequest toSave) {
		CakeEntity cakeEntity = mapper.toEntity(toSave);
		CakeEntity savedEntity = repository.save(cakeEntity);
		counterManager.increment(CounterName.SAVE);
		return mapper.toDTO(savedEntity);
	}

	@Transactional
	public CakeDTO update(Long id, CakeRequest toSave) {
		CakeEntity oldEntity = repository.findById(id).orElseThrow(() -> {
			log.warn("Could not find cake to update", keyValue("cakeId", id));
			return new IllegalArgumentException();
		});
		CakeEntity newPartialEntity = mapper.toEntity(toSave);
		newPartialEntity.setId(oldEntity.getId());

		counterManager.increment(CounterName.UPDATE);
		return mapper.toDTO(repository.save(newPartialEntity));
	}

	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			log.warn("Could not find cake to delete", keyValue("cakeId", id));
			throw new IllegalArgumentException();
		}
		counterManager.increment(CounterName.DELETE);
		repository.deleteById(id);
	}
}
