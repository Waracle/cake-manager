package com.waracle.cakemgr.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public interface FileStorageService {
    Path save(MultipartFile file) ;

    Optional<Resource> load(String filename);

    void deleteAll();

    Stream<Path> loadAll();
}
