package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.dto.ProductDTO;
import com.alexandersu.market_place_rest.entity.Product;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.exceptions.ProductNotFoundException;
import com.alexandersu.market_place_rest.mappers.ProductMapper;
import com.alexandersu.market_place_rest.repository.ProductRepository;
import com.alexandersu.market_place_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserService userService;

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

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        log.info("Delete product with id: {}", productId);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    // возвращает все объявления из бд удовлетворяющие поисковому слову
    public List<Product> getListProducts(String title) {
        if (title != null)
            return productRepository.findByTitleContainingIgnoreCase(title);
        return productRepository.findAll();
    }

    //Метод который будет возвращать все товары юзера, на странице юзера
    public List<Product> getAllProductFromUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return productRepository.findAllByUserOrderByCreateDateDesc(user);
    }


    // проверка на принадлежность объявления/продукта текущему пользователю
    public static boolean isEquals(User currentUser, Product product) {
        log.info("Сhecking for compliance of the user with email {} and the product{}", currentUser.getEmail(), product.getTitle());
        return currentUser.getEmail().equals(product.getUser().getEmail());
    }


    public Product updateProduct(ProductDTO productDTO,
                                 Principal principal,
                                 String productId) {
        Product currentProduct = getProductById(Long.parseLong(productId));
        User currentUser = userService.getCurrentUser(principal);
        if (isEquals(currentUser, currentProduct)) {
            currentProduct = ProductMapper.INSTANCE.ProductDTOtoProduct(productDTO);
            currentProduct.setUser(currentUser);
            log.info("Update product with title {}", productDTO.getTitle());
            return productRepository.save(currentProduct);
        }
        return null;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}

