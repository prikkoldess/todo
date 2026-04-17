package com.example.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.todo.model.Role;
import com.example.todo.model.User;
import com.example.todo.model.UserCreateDto;
import com.example.todo.model.UserDto;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    private UserCreateDto dto;
    private User user;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void testRegisterUser() {
        UserCreateDto dto = new UserCreateDto();
        dto.setEmail("test@gmail.com");
        dto.setPassword("userpass");
        dto.setUsername("user");

        when(passwordEncoder.encode("userpass")).thenReturn("encoded_pass");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserDto result = userService.registerUser(dto);

        assertEquals("user", result.getUsername());
        assertEquals("test@gmail.com", result.getEmail());
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals(Role.ROLE_USER, savedUser.getRole());
        assertEquals("encoded_pass", savedUser.getPassword());
    }

    @Test
    void testRegisterAdmin() {
        UserCreateDto dto = new UserCreateDto();
        dto.setEmail("admin@gmail.com");
        dto.setPassword("adminpass");
        dto.setUsername("admin");

        when(passwordEncoder.encode("adminpass")).thenReturn("encoded_pass");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserDto result = userService.registerAdmin(dto);

        assertEquals("admin", result.getUsername());
        assertEquals("admin@gmail.com", result.getEmail());
        verify(userRepository).save(userCaptor.capture());

        User savedAdmin = userCaptor.getValue();

        assertEquals(Role.ROLE_ADMIN, savedAdmin.getRole());
        assertEquals("encoded_pass", savedAdmin.getPassword());

    }
}
