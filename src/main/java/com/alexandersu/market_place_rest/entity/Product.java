package com.alexandersu.market_place_rest.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private Long id;
    private String title;
    private String description;
    private int price;
    private String city;
    private Category category;
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime createDate;
    private User user;

    protected void init() {
        createDate = LocalDateTime.now();
    }

}
