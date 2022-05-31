package com.waracle.cakemgr.service;

import com.waracle.manager.cake.model.Cake;
import com.waracle.manager.cake.model.CakeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CakeManagementService {

    CakeResponse addCake(String title, String description, MultipartFile image);

    List<Cake> getCakes();
}
