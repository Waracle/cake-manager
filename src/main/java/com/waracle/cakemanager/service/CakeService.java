package com.waracle.cakemanager.service;

import com.waracle.cakemanager.entity.CakeEntity;
import com.waracle.cakemanager.exception.CakeAlreadyPresentException;
import com.waracle.cakemanager.exception.CakeNotAvailableException;

import java.util.List;

public interface CakeService {
    CakeEntity saveCake(CakeEntity cake) throws CakeAlreadyPresentException;

    List<CakeEntity> getAllCakes() throws CakeNotAvailableException;

    CakeEntity getCakeById(Long id) throws CakeNotAvailableException;

    CakeEntity updateCake(Long id, CakeEntity cakeEntity) throws CakeNotAvailableException;

    CakeEntity deleteCakeById(Long id) throws CakeNotAvailableException;
}
