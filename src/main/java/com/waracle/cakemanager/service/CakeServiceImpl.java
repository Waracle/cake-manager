package com.waracle.cakemanager.service;

import com.waracle.cakemanager.entity.CakeEntity;
import com.waracle.cakemanager.exception.CakeAlreadyPresentException;
import com.waracle.cakemanager.exception.CakeNotAvailableException;
import com.waracle.cakemanager.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CakeServiceImpl implements CakeService {

    @Autowired
    CakeRepository cakeRepository;

    @Override
    public List<CakeEntity> getAllCakes() throws CakeNotAvailableException {
        List<CakeEntity> cakeList = cakeRepository.findAll();
        if (cakeList.isEmpty()) {
            throw new CakeNotAvailableException();
        }
        return cakeList;
    }

    @Override
    public CakeEntity getCakeById(Long id) throws CakeNotAvailableException {
        Optional<CakeEntity> cakeEntityOptional = cakeRepository.findById(id);
        if (cakeEntityOptional.isEmpty()) {
            throw new CakeNotAvailableException();
        }
        return cakeEntityOptional.get();
    }

    @Override
    public CakeEntity saveCake(CakeEntity cake) throws CakeAlreadyPresentException {
        if (null != cake.getCakeId() && cakeRepository.existsById(cake.getCakeId())) {
            throw new CakeAlreadyPresentException();
        }
        CakeEntity cakeEntity = cakeRepository.save(cake);
        return cakeEntity;
    }

    @Override
    public CakeEntity updateCake(Long id, CakeEntity cakeEntity) {
        if (!cakeRepository.existsById(id)) {
            throw new CakeNotAvailableException();
        }
        cakeEntity.setCakeId(id);
        return cakeRepository.save(cakeEntity);
    }

    @Override
    public CakeEntity deleteCakeById(Long id) {
        CakeEntity cake;
        Optional optional = cakeRepository.findById(id);
        if (optional.isPresent()) {
            cake = cakeRepository.findById(id).get();
            cakeRepository.deleteById(id);
        } else {
            throw new CakeNotAvailableException();
        }
        return cake;
    }
}
