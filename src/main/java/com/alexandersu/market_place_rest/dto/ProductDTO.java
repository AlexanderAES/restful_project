package com.alexandersu.market_place_rest.dto;

import com.alexandersu.market_place_rest.entity.Image;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private int price;
    private String city;
    private List<Image> images;
}
