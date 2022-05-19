package com.alexandersu.market_place_rest.controller;

import com.alexandersu.market_place_rest.payload.response.MessageResponse;
import com.alexandersu.market_place_rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/confirm")
@RequiredArgsConstructor

public class ConfirmController {

    private final UserService userService;

    //подтверждение email по токену
    @GetMapping("/activate/{token}")
    public ResponseEntity<MessageResponse> confirm(@PathVariable("token") String token) {
        userService.activateUser(token);
        return new ResponseEntity<>(new MessageResponse("User was activate"), HttpStatus.OK);
    }


}
