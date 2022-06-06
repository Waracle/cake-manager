package com.waracle.cakemgr.service;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.exception.InvalidCakeException;
import com.waracle.cakemgr.repository.CakeRepository;
import com.waracle.manager.cake.model.Cake;
import com.waracle.manager.cake.model.CakeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
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
        return new Cake().description(cakeEntity.getDescription()).title(cakeEntity.getTitle()).imageFilename(FilenameUtils.getName(cakeEntity.getImageUrl()));
    }

    private boolean isValidImage(MultipartFile image) {
        return Objects.requireNonNull(image.getContentType()).equalsIgnoreCase(MediaType.IMAGE_JPEG_VALUE)
                || image.getContentType().equalsIgnoreCase("image/jpg")
                || image.getContentType().equalsIgnoreCase(MediaType.IMAGE_PNG_VALUE);
    }

}
