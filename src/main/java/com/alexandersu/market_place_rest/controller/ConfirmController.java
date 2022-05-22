package com.alexandersu.market_place_rest.controller;

import com.alexandersu.market_place_rest.payload.response.MessageResponse;
import com.alexandersu.market_place_rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/confirm")
@RequiredArgsConstructor
@Tag(name = "Confirmation registration",
        description = "контроллер для подтверждения регистрации пользователей через email.")
public class ConfirmController {

    private final UserService userService;

    @GetMapping("/activate/{token}")
    @Operation(summary = "подтверждение email",
            description = "Позволяет активировать аккаунт пользователя по ссылке отправленной на email пользователя")
    public ResponseEntity<MessageResponse> confirm(@PathVariable("token") String token) {
        userService.activateUser(token);
        return new ResponseEntity<>(new MessageResponse("User was activate"), HttpStatus.OK);
    }

}
