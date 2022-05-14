package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.dto.UserDTO;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.entity.enums.Role;


import com.alexandersu.market_place_rest.exceptions.UserExistException;
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
        user.setPhoneNumber(userIn.getPhoneNumber());
        user.setName(userIn.getName());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(Role.ROLE_ADMIN);
        user.setActive(true);

        try {
            log.info("Saving User {}",userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e){
            log.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername()+" already exist. Please check credentials");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setName(user.getName());
        user.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal){
        String email = principal.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with email: " + email));
    }

    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    public User getUserById(Long id){
        return userRepository.findUserById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }


}

