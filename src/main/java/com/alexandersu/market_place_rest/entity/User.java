package com.alexandersu.market_place_rest.entity;

import com.alexandersu.market_place_rest.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, updatable = false)
    private String email;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(unique = true, updatable = false)
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String name;

    @Column(name = "active")
    private boolean active;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image avatar;

    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createDate;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public boolean isActive() {
        return active;
    }

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User(Long id,
                String email,
                String phoneNumber,
                String name,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    public User(Long id, String email, String username, String phoneNumber, String name) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    @Override
    public String getUsername() {return username;}

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
