package com.alexandersu.market_place_rest.controller;

import com.alexandersu.market_place_rest.dto.UserDTO;
import com.alexandersu.market_place_rest.mappers.UserMapper;
import com.alexandersu.market_place_rest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@CrossOrigin
@RequiredArgsConstructor
@Log4j2
public class AdminController {

    private final UserService userService;

    @PostMapping("/ban/{userId}")
    public ResponseEntity<Object> userBan(@PathVariable("userId") String userId) {
        userService.banUserById(userId);
        log.info("User activation changed with id {} ", userId);
        return ResponseEntity.status(HttpStatus.OK).body("User activation changed");
    }

    @GetMapping("/all/users")
    public ResponseEntity<List<UserDTO>> getListUsers() {
        List<UserDTO> userDTOList = userService.getListUsers()
                .stream()
                .map(UserMapper.INSTANCE::UserToUserDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

}
