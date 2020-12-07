package com.waracle.cakemgr.service;

import com.waracle.cakemgr.Cake;

import java.util.List;

public interface DaoService {

    void add(Cake cake);

    Cake getCakeByTitle(String title);

    Cake getCake(Integer id);

    List<Cake> getAllCakes();

    boolean checkCakeAlreadyExists(Cake cake);

}
