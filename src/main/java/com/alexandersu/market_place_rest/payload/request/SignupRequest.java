package com.alexandersu.market_place_rest.payload.request;

import com.alexandersu.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;

    @NotEmpty(message = "Please enter your phone number")
    private String phoneNumber;

    @NotEmpty(message = "Please enter your name")
    private String name;

    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;
    private  String confirmPassword;
}
