package com.alexandersu.market_place_rest.repository;

import com.alexandersu.market_place_rest.entity.Product;
import com.alexandersu.market_place_rest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByTitleContainingIgnoreCase(String title);
    List<Product> findAllByUserOrderByCreateDateDesc(User user);
}
