package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.dto.UserDTO;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.entity.enums.Role;


import com.alexandersu.market_place_rest.exceptions.UserExistException;
import com.alexandersu.market_place_rest.mappers.UserMapper;
import com.alexandersu.market_place_rest.payload.request.SignupRequest;
import com.alexandersu.market_place_rest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User createUser(SignupRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setUsername(userIn.getUsername());
        user.setName(userIn.getName());
        user.setPhoneNumber(userIn.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(Role.ROLE_ADMIN);

        try {
            log.info("Saving user {}",userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e){
            log.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername()+" already exist. Please check credentials");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        log.info("Update user with email {}",user.getEmail());
        return userRepository.save(user);

    }
    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }

    public User getUserById(Long id){
        return userRepository.findUserById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

}

