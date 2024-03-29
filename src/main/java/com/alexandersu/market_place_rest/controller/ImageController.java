package com.alexandersu.market_place_rest.controller;

import com.alexandersu.market_place_rest.entity.Image;
import com.alexandersu.market_place_rest.payload.response.MessageResponse;
import com.alexandersu.market_place_rest.repository.ImageRepository;
import com.alexandersu.market_place_rest.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/images")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Images", description = "контроллер для работы с изображениями")
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @PostMapping("/{productId}/upload")
    @Operation(summary = "загрузка изображения", description = "позволяет загрузить изображение к товару по его id")
    public ResponseEntity<MessageResponse> uploadImageToProduct(
            @PathVariable("productId") String productId,
            @RequestParam("file") MultipartFile file,
            Principal principal) throws IOException {
        imageService.uploadImageToProduct(file, principal, Long.parseLong(productId));
        return ResponseEntity.ok(new MessageResponse("Image upload successfully"));

    }

    @GetMapping("/{productId}/image")
    @Operation(summary = "просмотр", description = "позволяет посмотреть изображение товара по его id")
    private ResponseEntity<?> getImageById(@PathVariable Long productId) {
        Image image = imageRepository.findByProductId(productId);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}

