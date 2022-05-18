package com.alexandersu.market_place_rest.controller;

import com.alexandersu.market_place_rest.dto.UserDTO;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.mappers.UserMapper;
import com.alexandersu.market_place_rest.service.UserService;
import com.alexandersu.market_place_rest.validations.ResponseErrorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    // метод возвращает пользователя который находится авторизированным в системе
    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = UserMapper.INSTANCE.UserToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // метод возвращает пользователя по его id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = UserMapper.INSTANCE.UserToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // метод для изменения профиля пользователя
    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);

        UserDTO userUpdated = UserMapper.INSTANCE.UserToUserDTO(user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

}
