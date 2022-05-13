package com.alexandersu.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserExistExseption extends RuntimeException{
    public UserExistExseption(String message){super(message);}
}
