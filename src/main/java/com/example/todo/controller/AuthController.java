package com.example.todo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.model.LoginDto;
import com.example.todo.model.UserCreateDto;
import com.example.todo.model.UserDto;
import com.example.todo.service.AuthService;
import com.example.todo.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registrUser(@RequestBody UserCreateDto request) {
        UserDto createdUser = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

}
