package com.waracle.cakemgr.service;

import com.waracle.cakemgr.config.FileStorageProperties;
import com.waracle.cakemgr.exception.InvalidCakeException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Setter
@Getter
@Slf4j
@Service
public class LocalFileStorageService implements FileStorageService {

    private  Path folder;

    private final FileStorageProperties fileStorageProperties;

    public LocalFileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
        folder = new File(fileStorageProperties.getUploadDir()).toPath();
    }

    @PostConstruct
    public void init() {
        try {
            if (Files.exists(folder)){
                return;
            }
            Files.createDirectory(folder);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!",e);
        }
    }

    @Override
    public Path save(MultipartFile file){
        if (file.isEmpty()) {
            throw new InvalidCakeException("File cannot be empty");
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = folder.resolve(Paths.get(Objects.requireNonNull(file.getOriginalFilename())));
            Files.write(path, bytes);
            return path;
        } catch (IOException e) {
            throw new RuntimeException("Error occurred whilst saving file",e);
        }
    }

    /**
     * Loads a file as a byte array
     * @return
     */
    @Override
    public byte[] load(String filename) {
        byte[] image = new byte[0];
        try {
            Path path = folder.resolve(Paths.get(Objects.requireNonNull(filename)));
            image = FileUtils.readFileToByteArray(path.toFile());
        } catch (IOException e) {
            log.error("IOException occurred whilst reading file: " + filename, e);
        }
        return image;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(folder.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.folder, 1).filter(path -> !path.equals(this.folder)).map(this.folder::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
