package com.alexandersu.market_place_rest.controller;


import com.alexandersu.market_place_rest.dto.ProductDTO;
import com.alexandersu.market_place_rest.entity.Product;
import com.alexandersu.market_place_rest.mappers.ProductMapper;
import com.alexandersu.market_place_rest.payload.response.MessageResponse;
import com.alexandersu.market_place_rest.service.ProductService;
import com.alexandersu.market_place_rest.service.UserService;
import com.alexandersu.market_place_rest.validations.ResponseErrorValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("api/v1/products")
@CrossOrigin
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Products", description = "контроллер для работы с объявлениями")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    @PostMapping("/create")
    @Operation(summary = "создание объявления", description = "позволяет создать объявление о продукте или услуге")
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

    @GetMapping("/info/{productId}")
    @Operation(summary = "информация о продукте", description = "получение информации о продукте или услуге")
    public ResponseEntity<ProductDTO> getProductInfo(@PathVariable("productId") String productId) {
        Product product = productService.getProductById(Long.parseLong(productId));
        ProductDTO productDTOInfo = ProductMapper.INSTANCE.ProductToProductDTO(product);
        log.info("Get info about product with id {}", productId);
        return new ResponseEntity<>(productDTOInfo, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "все объявления", description = "позволяет получить список всех объявлений")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOList = productService.getAllProduct()
                .stream()
                .map(ProductMapper.INSTANCE::ProductToProductDTO)
                .collect(Collectors.toList());
        log.info("Get get all products");
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    @Operation(summary = "удаление объявления", description = "удаление продукта/услуги")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable("productId") String productId) {
        productService.deleteProduct(Long.parseLong(productId));
        log.info("Delete product with id {}", productId);
        return new ResponseEntity<>(new MessageResponse("Product was deleted"), HttpStatus.OK);
    }

    @GetMapping("/search/{title}")
    @Operation(summary = "поиск по заголовку", description = "получение списка всех объявлений по ключевому слову")
    public ResponseEntity<List<ProductDTO>> getListProduct(@PathVariable("title") String title) {
        List<ProductDTO> productDTOList = productService.getListProducts(title)
                .stream()
                .map(ProductMapper.INSTANCE::ProductToProductDTO)
                .collect(Collectors.toList());
        log.info("Search for a product by the word {}", title);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/products")
    @Operation(summary = "Получение списка всех объявлений пользователя",
            description = "получение списка всех продуктов или услуг предлагаемых пользователем")
    public ResponseEntity<List<ProductDTO>> getAllProductsForUser(Principal principal) {
        List<ProductDTO> productDTOList = productService.getAllProductFromUser(principal)
                .stream()
                .map(ProductMapper.INSTANCE::ProductToProductDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    @Operation(summary = "обновление объявления", description = "обновление объявления")
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


