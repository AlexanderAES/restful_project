package com.alexandersu.market_place_rest.repository;

import com.alexandersu.market_place_rest.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
   Image findByProductId(Long productId);
}
