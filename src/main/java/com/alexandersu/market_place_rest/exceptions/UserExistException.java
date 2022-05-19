package com.alexandersu.market_place_rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
