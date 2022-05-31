package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.exception.InvalidCakeException;
import com.waracle.cakemgr.repository.CakeRepository;
import com.waracle.manager.cake.model.Cake;
import com.waracle.manager.cake.model.CakeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CakManagementServiceImpl implements CakeManagementService {

    private final CakeRepository cakeRepository;

    private final FileStorageService fileService;


    @Override
    public CakeResponse addCake(String title, String description, MultipartFile image) {

        if (!isValidImage(image)){
            throw new InvalidCakeException("Incorrect Content type. Valid Content types are image/jpeg, image/png");
        }

        Path path = fileService.save(image);
        CakeEntity entity =  CakeEntity.builder().imageUrl(path.toUri().toString()).description(description).title(title).build();
        entity = cakeRepository.save(entity);

        return new CakeResponse()
                .id(entity.getId())
                .fileName(image.getOriginalFilename())
                .uri(path.toUri().toString())
                .size(image.getSize());

    }

    @Override
    public List<Cake> getCakes() {
        return cakeRepository.findAll().stream().map(this::toCake).collect(Collectors.toList());
    }

    private Cake toCake(CakeEntity cakeEntity) {
        try {
            return new Cake().description(cakeEntity.getDescription()).title(cakeEntity.getTitle()).image(getBytes(cakeEntity));
        } catch (IOException e) {
            log.error(e.getMessage(),e.fillInStackTrace());
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    private byte[] getBytes(CakeEntity cakeEntity) throws IOException {
        Optional<Resource> optionalResource = fileService.load(cakeEntity.getImageUrl());
        return optionalResource.isPresent() ? optionalResource.get() .getInputStream().readAllBytes() : new byte[] {};
    }

    private boolean isValidImage(MultipartFile image) {
        return Objects.requireNonNull(image.getContentType()).equalsIgnoreCase("image/jpeg")
                || image.getContentType().equalsIgnoreCase("image/jpg")
                || image.getContentType().equalsIgnoreCase("image/png");
    }

}
