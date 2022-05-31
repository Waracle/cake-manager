package com.waracle.cakemgr.service;

import com.waracle.cakemgr.config.FileStorageProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LocalFileStorageServiceTest {

    private final Path uploads = Path.of("uploads");

    private LocalFileStorageService localFileStorageService;

    @BeforeEach
    void setUp() throws IOException {
        FileStorageProperties fileStorageProperties = new FileStorageProperties();
        fileStorageProperties.setUploadDir("uploads");
        localFileStorageService = new LocalFileStorageService(fileStorageProperties);
        Files.createDirectory(uploads);
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
        Optional<Resource> resource = localFileStorageService.load(uploads.resolve(Paths.get("hello.txt")).toUri().toString());

        //Then
        assertTrue(resource.isPresent());
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