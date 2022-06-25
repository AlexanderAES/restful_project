package com.alexandersu.market_place_rest.service;

import com.alexandersu.market_place_rest.dto.UserDTO;
import com.alexandersu.market_place_rest.entity.User;
import com.alexandersu.market_place_rest.exceptions.UserNotFoundException;
import com.alexandersu.market_place_rest.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Spy
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Principal principal;
    @Mock
    private User userStub;

    private String defaultName = "defaultName";

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setup() throws Exception {
        userRepository = mock(UserRepository.class);
    }

    private UserDTO createValidUserDTO() {
        return UserDTO.builder()
                .name("testName")
                .email("test@mail.io")
                .username("test")
                .phoneNumber("+71234567890")
                .build();
    }

    private User createValidUser() {
        return User.builder()
                .name("testName")
                .email("test@mail.io")
                .username("testUserName")
                .phoneNumber("+71234567890")
                .activationCode("activationCode")
                .password("password")
                .active(false).build();
    }

    @Test
    void createUser() {
        User user = new User();
        user.setEmail("test@mail.io");
        user.setUsername("testUserName");
        user.setName("testName");
        user.setPhoneNumber("+71234567890");
        user.setPassword("password");

        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        User created = userRepository.save(user);
        Mockito.verify(userRepository).save(created);
    }

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
