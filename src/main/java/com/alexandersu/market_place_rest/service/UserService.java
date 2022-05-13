package com.alexandersu.market_place_rest.service;

import com.alexandersu.exceptions.UserExistExseption;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.entity.enums.Role;
import com.alexandersu.market_place_rest.payload.request.SignupRequest;
import com.alexandersu.market_place_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User createUser(SignupRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setPhoneNumber(userIn.getPhoneNumber());
        user.setName(userIn.getName());
        user.getRoles().add(Role.ROLE_ADMIN);
        user.setActive(true);
        try {
            log.info("Saving User {}",userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e){
            log.error("Error during registration. {}", e.getMessage());
            throw new UserExistExseption("The user " + user.getUsername()+" already exist. Please check credentials");
        }
    }
}

