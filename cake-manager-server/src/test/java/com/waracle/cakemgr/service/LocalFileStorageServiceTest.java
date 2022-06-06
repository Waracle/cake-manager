package com.waracle.cakemgr.service;

import com.waracle.cakemgr.config.FileStorageProperties;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LocalFileStorageServiceTest {

    private Path uploads;

    private LocalFileStorageService localFileStorageService;

    @BeforeEach
    void setUp() throws IOException {
        uploads = Files.createTempDirectory(Paths.get("target"), "tmpDirPrefix");
        FileStorageProperties fileStorageProperties = new FileStorageProperties();
        fileStorageProperties.setUploadDir(uploads.toString());
        localFileStorageService = new LocalFileStorageService(fileStorageProperties);


    }

    @AfterEach
    void cleanUp() {
        FileSystemUtils.deleteRecursively(uploads.toFile());
    }

    @Test
    void save() {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.IMAGE_PNG_VALUE,
                "Hello, World!".getBytes()
        );

        localFileStorageService.save(file);

        assertTrue(Files.exists(uploads.resolve(Paths.get("hello.txt"))));
    }

    @Test
    void load() {
        //Given
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.IMAGE_PNG_VALUE,
                "Hello, World!".getBytes()
        );

        localFileStorageService.save(file);

        //When
        byte[] resource = localFileStorageService.load("hello.txt");

        //Then
        assertNotNull(resource);
    }

    @Test
    void deleteAll() {
        localFileStorageService.deleteAll();
        assertFalse(uploads.toFile().exists());
    }

    @Test
    void loadAll() {
        //Given
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.IMAGE_PNG_VALUE,
                "Hello, World!".getBytes()
        );

        localFileStorageService.save(file);

        //When
        Stream<Path> stream = localFileStorageService.loadAll();


        assertEquals(1, stream.count());

    }
}