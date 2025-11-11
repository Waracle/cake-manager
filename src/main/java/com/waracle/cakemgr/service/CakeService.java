package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.CakeDto;

import java.util.List;

public interface CakeService {
    
    List<CakeDto> getAllCakes();
    
    CakeDto createCake(CakeDto cakeDto);
    
    CakeDto updateCake(Long id, CakeDto cakeDto);
    
    void deleteCake(Long id);
}
