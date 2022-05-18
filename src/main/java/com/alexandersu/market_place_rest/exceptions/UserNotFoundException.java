package com.alexandersu.market_place_rest.exceptions;

public class UserNotFoundException extends  RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id: '%s' не найден", id));
    }
}
