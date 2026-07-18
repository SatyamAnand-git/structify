package com.structify.service;

import com.structify.dto.RegisterRequest;
import com.structify.dto.RegisterResponse;
import com.structify.entity.User;
import com.structify.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public RegisterResponse registerUser(RegisterRequest request) {
        String normalizedEmail = request.getEmail()
                .trim()
                .toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(normalizedEmail);
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        User savedUser = userRepository.save(user);
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }
}