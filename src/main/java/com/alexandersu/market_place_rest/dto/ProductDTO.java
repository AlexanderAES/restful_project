package com.alexandersu.market_place_rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Сущность объявления")
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
