package com.alexandersu.market_place_rest.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private Long id;
    private String categoryName;
    private List<Product> products = new ArrayList<>();
}
