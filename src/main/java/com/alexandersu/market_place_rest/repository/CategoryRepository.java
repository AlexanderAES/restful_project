package com.alexandersu.market_place_rest.repository;

import com.alexandersu.market_place_rest.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
