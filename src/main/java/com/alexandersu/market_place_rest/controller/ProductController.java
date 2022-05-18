package com.alexandersu.market_place_rest.controller;


import com.alexandersu.market_place_rest.dto.ProductDTO;
import com.alexandersu.market_place_rest.entity.Product;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.mappers.ProductMapper;
import com.alexandersu.market_place_rest.payload.response.MessageResponse;
import com.alexandersu.market_place_rest.service.ProductService;
import com.alexandersu.market_place_rest.service.UserService;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/product")
@CrossOrigin
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;


    // создание продукта
    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody
                                                        ProductDTO productDTO,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Product product = productService.createProduct(productDTO, principal);

        ProductDTO createdProduct = ProductMapper.INSTANCE.ProductToProductDTO(product);
        log.info("Save new product with name {} to database", productDTO.getTitle());
        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }

    // информация о продукте по его id
    @GetMapping("/info/{productId}")
    public ResponseEntity<ProductDTO> getProductInfo(@PathVariable("productId") String productId) {
        Product product = productService.getProductById(Long.parseLong(productId));
        ProductDTO productDTOInfo = ProductMapper.INSTANCE.ProductToProductDTO(product);
        log.info("Get info about product with id {}", productId);
        return new ResponseEntity<>(productDTOInfo, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOList = productService.getAllProduct()
                .stream()
                .map(ProductMapper.INSTANCE::ProductToProductDTO)
                .collect(Collectors.toList());
        log.info("Get get all products");
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }


    // удаление продукта по его id
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable("productId") String productId) {
        productService.deleteProduct(Long.parseLong(productId));
        log.info("Delete product with id {}", productId);
        return new ResponseEntity<>(new MessageResponse("Product was deleted"), HttpStatus.OK);
    }

    // поиск продукта по ключевому слову
    @GetMapping("/search/{title}")
    public ResponseEntity<List<ProductDTO>> getListProduct(@PathVariable("title") String title) {
        List<ProductDTO> productDTOList = productService.getListProducts(title)
                .stream()
                .map(ProductMapper.INSTANCE::ProductToProductDTO)
                .collect(Collectors.toList());
        log.info("Search for a product by the word {}", title);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    // список всех продуктов пользователя
    @GetMapping("/user/products")
    public ResponseEntity<List<ProductDTO>> getAllProductsForUser(Principal principal) {
        List<ProductDTO> productDTOList = productService.getAllProductFromUser(principal)
                .stream()
                .map(ProductMapper.INSTANCE::ProductToProductDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    // редактирование объявления поьзователем при условии что это его объявление
    @PutMapping("/update/{productId}")
    public ResponseEntity<Object> updateProduct(@PathVariable("productId") String productId, @Valid @RequestBody
            ProductDTO productDTO, BindingResult bindingResult, Principal principal) {

        Product product = productService.updateProduct(productDTO, principal, productId);
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors)) return errors;
        ProductDTO productUpdated = ProductMapper.INSTANCE.ProductToProductDTO(product);

        if (productUpdated != null) return new ResponseEntity<>(productUpdated, HttpStatus.OK);
        log.info("Update product with name {} to database", productDTO.getTitle());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The product has not been updated");

    }
}


