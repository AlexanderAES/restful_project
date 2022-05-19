package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.entity.Image;
import com.alexandersu.market_place_rest.entity.Product;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.exceptions.ImageNotFoundException;
import com.alexandersu.market_place_rest.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;

    public Image uploadImageToProduct(MultipartFile file, Principal principal, Long productId) throws IOException {
        User user = productService.getUserByPrincipal(principal);
        Product product = user.getProducts()
                .stream()
                .filter(p -> p.getId().equals(productId))
                .collect(productCollector());

        Image image = new Image();
        image.setProduct(product);
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        log.info("Uploading image to product {}", product.getId());
        return imageRepository.save(image);
    }


    private <T> Collector<T, ?, T> productCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

}
