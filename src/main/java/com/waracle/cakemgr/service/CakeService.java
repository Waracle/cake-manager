package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.CakeDto;

import java.util.List;

public interface CakeService {
    
    List<CakeDto> getAllCakes();
    
    CakeDto createCake(CakeDto cakeDto);
    
    void loadInitialData();
}
