package com.alexandersu.market_place_rest.controller;

import com.alexandersu.market_place_rest.dto.UserDTO;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.mappers.UserMapper;
import com.alexandersu.market_place_rest.service.UserService;
import com.alexandersu.market_place_rest.validations.ResponseErrorValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Users", description = "контроллер для работы с пользователями")
public class UserController {

    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

        @GetMapping("/")
    @Operation(summary = "получение пользователя", description = "Позволяет получить пользователя который авторизован в системе")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = UserMapper.INSTANCE.UserToUserDTO(user);
        log.info("Getting the current authorized user with id {} ", user.getId());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "информация о пользователе", description = "позволяет получить профайл пользователя")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId) {
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = UserMapper.INSTANCE.UserToUserDTO(user);
        log.info("Get profile user with id {} ", user.getId());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "изменение профиля пользователя", description = "позволяет изменить информацию в профиле пользователя")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);

        UserDTO userUpdated = UserMapper.INSTANCE.UserToUserDTO(user);
        log.info("Update profile user with id {} ", user.getId());
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

}
