package com.alexandersu.market_place_rest.controller;


import com.alexandersu.market_place_rest.payload.request.LoginRequest;
import com.alexandersu.market_place_rest.payload.request.SignupRequest;
import com.alexandersu.market_place_rest.payload.response.JWTTokenSuccessResponse;
import com.alexandersu.market_place_rest.payload.response.MessageResponse;
import com.alexandersu.market_place_rest.security.JWTTokenProvider;
import com.alexandersu.market_place_rest.security.SecurityConstants;
import com.alexandersu.market_place_rest.service.UserService;
import com.alexandersu.market_place_rest.validations.ResponseErrorValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "контроллер для авторизации и регистрации пользователей")
public class AuthController {

    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ResponseErrorValidation responseErrorValidation;
    private final UserService userService;

    @PostMapping("/signin")
    @Operation(summary = "вход пользователя в приложение", description = "Позволяет авторизовать пользователя в приложении")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        if (userService.сheckUserEnableDisable(loginRequest.getEmail())) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generatedToken(authentication);

            return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User baned!");
    }

    @PostMapping("/signup")
    @Operation(summary = "регистрация пользователя", description = "Позволяет зарегистрировать пользователя")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        userService.createUser(signupRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}

