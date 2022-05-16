package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.dto.ProductDTO;
import com.alexandersu.market_place_rest.entity.Product;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.mappers.ProductMapper;
import com.alexandersu.market_place_rest.repository.ProductRepository;
import com.alexandersu.market_place_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // метод для создания объявления
    public Product createProduct(ProductDTO productDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Product product = ProductMapper.INSTANCE.DTOmapToProduct(productDTO);
        log.info("Saving Post for User: {}", user.getEmail());
        return productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

}
