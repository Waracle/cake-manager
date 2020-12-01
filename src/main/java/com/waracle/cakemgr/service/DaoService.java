package com.waracle.cakemgr.service;

import com.waracle.cakemgr.Cake;

import java.util.List;

public interface DaoService {

    void add(Cake cake);

    Cake getCake(Integer id);

    List<Cake> getAllCakes();

}
