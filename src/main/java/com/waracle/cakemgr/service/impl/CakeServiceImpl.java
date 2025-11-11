package com.waracle.cakemgr.service.impl;

import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;
import com.waracle.cakemgr.service.CakeService;
import com.waracle.cakemgr.util.CakeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CakeServiceImpl implements CakeService {

    @Autowired
    private CakeRepository cakeRepository;

    @Autowired
    private CakeMapper cakeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CakeDto> getAllCakes() {
        return cakeRepository.findAll()
            .stream()
            .map(cakeMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public CakeDto createCake(CakeDto cakeDto) {
        CakeEntity cake = cakeMapper.toEntity(cakeDto);
        CakeEntity savedCake = cakeRepository.save(cake);
        return cakeMapper.toDto(savedCake);
    }

    @Override
    public CakeDto updateCake(Long id, CakeDto cakeDto) {
        CakeEntity existingCake = cakeRepository.findById(id)
            .orElseThrow(() -> new com.waracle.cakemgr.exception.CakeNotFoundException("Cake not found with id: " + id));
        
        existingCake.setTitle(cakeDto.getTitle());
        existingCake.setDescription(cakeDto.getDescription());
        existingCake.setImage(cakeDto.getImage());
        
        CakeEntity updatedCake = cakeRepository.save(existingCake);
        return cakeMapper.toDto(updatedCake);
    }

    @Override
    public void deleteCake(Long id) {
        if (!cakeRepository.existsById(id)) {
            throw new com.waracle.cakemgr.exception.CakeNotFoundException("Cake not found with id: " + id);
        }
        cakeRepository.deleteById(id);
    }
}
