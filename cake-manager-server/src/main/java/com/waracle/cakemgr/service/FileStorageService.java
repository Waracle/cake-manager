package com.waracle.cakemgr.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {
    Path save(MultipartFile file) ;

    byte[] load(String filename);

    void deleteAll();

    Stream<Path> loadAll();
}
