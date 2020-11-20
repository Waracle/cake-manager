package com.waracle.cakemgr.service;

import com.waracle.cakemgr.CakeEntity;

import java.util.List;

public interface DaoService {

    void add(CakeEntity cake);

    CakeEntity getCake(Integer id);

    List<CakeEntity> getAllCakes();

}
