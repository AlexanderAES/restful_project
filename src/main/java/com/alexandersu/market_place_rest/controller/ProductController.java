package com.alexandersu.market_place_rest.controller;


import com.alexandersu.market_place_rest.dto.ProductDTO;
import com.alexandersu.market_place_rest.entity.Product;
import com.alexandersu.market_place_rest.mappers.ProductMapper;
import com.alexandersu.market_place_rest.service.ProductService;
import com.alexandersu.market_place_rest.validations.ResponseErrorValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/product")
@CrossOrigin
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;
    private final ResponseErrorValidation responseErrorValidation;


    @PostMapping("/create")
    public ResponseEntity<Object>createProduct(@Valid @RequestBody ProductDTO productDTO,
                                               BindingResult bindingResult,
                                               Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Product product = productService.createProduct(productDTO, principal);

        ProductDTO createdProduct = ProductMapper.INSTANCE.ProductToProductDTO(product);
        log.info("Save new products with name {} to database", productDTO.getTitle());

        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }
}
