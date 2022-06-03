package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.exceptions.UserNotFoundException;
import com.alexandersu.market_place_rest.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void getListUsers() {
        User user = User.builder().username("testName").password("123456").email("test@mail.io").phoneNumber("+123456789").build();
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user);
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);
        List<User> found = userService.getListUsers();
        verify(userRepository).findAll();

        assertThat(found).isNotNull();
        assertThat(found.size()).isEqualTo(3);
        assertThat(found.get(0)).isEqualTo(user);
    }

    @Test
    void getUserById() {
        Long id = 1L;
        String username = "testUserName";
        User expectedUser = User.builder().id(id).name(username).build();

        Mockito.when(userRepository.findUserById(Mockito.eq(id))).thenReturn(Optional.ofNullable(expectedUser));

        User actualUser = userService.getUserById(1L);

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
        assertDoesNotThrow(() -> new UserNotFoundException(1L));
    }
}