package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found with username: + " + username));
        return build(user);
    }

    public User loadUserById(Long id){
        return userRepository.findUserById(id).orElse(null);
    }

    public User build(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new User(user.getId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getName(),
                user.getPassword(),
                authorities);
    }
}
