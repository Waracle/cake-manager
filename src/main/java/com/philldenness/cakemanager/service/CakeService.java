package com.philldenness.cakemanager.service;

import java.util.List;

import com.philldenness.cakemanager.dto.CakeDTO;
import com.philldenness.cakemanager.mapper.CakeMapper;
import com.philldenness.cakemanager.repository.CakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CakeService {
	private final CakeRepository repository;
	private final CakeMapper cakeMapper;

	public List<CakeDTO> getCakes() {
		return repository.findAll().stream().map(cakeMapper::toDTO).toList();
	}
}
