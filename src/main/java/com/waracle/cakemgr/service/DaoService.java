package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entity.Cake;

import java.util.List;

public interface DaoService {

    Cake add(Cake cake);

    Cake getCakeByTitle(String title);

    Cake getCake(Integer id);

    List<Cake> getAllCakes();

    void deleteCake(Cake cake);

    boolean isCakeExists(Cake cake);

}
