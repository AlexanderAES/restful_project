package com.alexandersu.market_place_rest.dto;

import com.alexandersu.market_place_rest.entity.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    private Long id;
    @NotEmpty
    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private User user;
}
