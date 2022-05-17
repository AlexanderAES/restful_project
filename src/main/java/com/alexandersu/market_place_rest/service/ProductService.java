package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.dto.ProductDTO;
import com.alexandersu.market_place_rest.entity.Image;
import com.alexandersu.market_place_rest.entity.Product;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.exceptions.ProductNotFoundException;
import com.alexandersu.market_place_rest.mappers.ProductMapper;
import com.alexandersu.market_place_rest.repository.ProductRepository;
import com.alexandersu.market_place_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.ProviderNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // метод для создания объявления
    public Product createProduct(ProductDTO productDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Product product = ProductMapper.INSTANCE.ProductDTOtoProduct(productDTO);
        product.setUser(user);

        log.info("Saving product for User: {}", user.getEmail());
        return productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

    public void deleteProduct(Long productId){productRepository.deleteById(productId);}

    public Product getProductById(Long productId){
        return productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
    }

    // возвращает все объявления из бд удовлетворяющие поисковому слову
    public List<Product> getListProducts(String title){
        if (title != null)
            return productRepository.findByTitleContainingIgnoreCase(title);
        return productRepository.findAll();
    }

    //Метод который будет возвращать все товары юзера, на странице юзера
    public List<Product> getAllProductFromUser(Principal principal){
        User user = getUserByPrincipal(principal);
        return productRepository.findAllByUserOrderByCreateDateDesc(user);
    }

}

