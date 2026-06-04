package org.fleetflow.fleetflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.fleetflow.fleetflow.dto.AuthRequest;
import org.fleetflow.fleetflow.dto.AuthResponse;
import org.fleetflow.fleetflow.entity.User;
import org.fleetflow.fleetflow.repository.UserRepository;
import org.fleetflow.fleetflow.security.JwtService;
import org.fleetflow.fleetflow.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserService {
    private final UserRepository userRepository;
    private final JwtService jwtUtil;

    public AuthResponse registerUser(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .role(request.getRole())
                .build();

        user.setHashedPassword(request.getPassword());

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail(),
                savedUser.getRole().name(),
                savedUser.getId());

        return AuthResponse.builder()
                .token(token)
                .message("User registered successfully")
                .build();
    }

    public AuthResponse loginUser(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(),
                user.getRole().name(),
                user.getId());

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }
}
