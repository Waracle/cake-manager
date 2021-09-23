package com.waracle.cakes.service;

import java.util.List;

import com.waracle.cakes.model.dto.CakeDTO;

public interface CakeService {

	
	public List<CakeDTO> findCakes();
	
	public void createCake(CakeDTO cake);
}
