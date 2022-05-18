package com.alexandersu.market_place_rest.dto;

import com.alexandersu.market_place_rest.entity.User;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private int price;
    private String city;
    private String productOwnerName;
    private String productOwnerPhoneNumber;

}
