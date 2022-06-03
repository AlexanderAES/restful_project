package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.entity.Product;
import com.alexandersu.market_place_rest.exceptions.ProductNotFoundException;
import com.alexandersu.market_place_rest.exceptions.UserNotFoundException;
import com.alexandersu.market_place_rest.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getListProducts() {
        List<Product> products = new ArrayList<>();
        Product product = Product.builder().title("testTitle").description("testDescription").price(100).build();
        products.add(product);
        when(productRepository.findByTitleContainingIgnoreCase("test")).thenReturn(products);
        List<Product> found = productService.getListProducts("test");
        assertThat(found).isNotNull();
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isEqualTo(products.get(0));
    }

    @Test
    void getProductById() {
        Product product = Product.builder().id(1L).title("testTitle").description("testDescription").price(100).build();
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.getProductById(1L);
        assertDoesNotThrow(() -> new ProductNotFoundException(1L));
        when(productRepository.findById(2L)).thenThrow(new UserNotFoundException(2L));
        assertThrows(UserNotFoundException.class, () -> productService.getProductById(2L));
    }

    @Test
    void getAllProduct() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().id(1L).title("testTitle").description("testDescription").price(100).build());
        when(productRepository.findAll()).thenReturn(products);
        List<Product> found = productService.getAllProduct();
        verify(productRepository).findAll();
        assertThat(found).isNotNull();
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isEqualTo(products.get(0));
    }

}