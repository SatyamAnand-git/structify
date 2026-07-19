package com.structify.controller;

import com.structify.dto.LoginRequest;
import com.structify.dto.LoginResponse;
import com.structify.dto.RegisterRequest;
import com.structify.dto.RegisterResponse;
import com.structify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public RegisterResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return userService.loginUser(request);
    }
}