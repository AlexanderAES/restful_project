package com.alexandersu.market_place_rest.entity;

import com.alexandersu.market_place_rest.entity.enums.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private Long id;
    private String Email;
    private String phoneNumber;
    private String name;
    private boolean active;
    private Image avatar;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private LocalDateTime createDate;
    private List<Product> products = new ArrayList<>();

    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }
}
