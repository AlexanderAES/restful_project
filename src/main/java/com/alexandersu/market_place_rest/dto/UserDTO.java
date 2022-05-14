package com.alexandersu.market_place_rest.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String email;
}
