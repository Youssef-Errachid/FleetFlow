package org.fleetflow.fleetflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.fleetflow.fleetflow.dto.AuthRequest;
import org.fleetflow.fleetflow.dto.AuthResponse;
import org.fleetflow.fleetflow.entity.*;
import org.fleetflow.fleetflow.repository.*;
import org.fleetflow.fleetflow.security.JwtService;
import org.fleetflow.fleetflow.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ChauffeurRepository chauffeurRepository;
    private final JwtService jwtUtil;

    public AuthResponse registerUser(AuthRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user;

        if (request.getRole() == Role.CLIENT) {

            Client client = Client.builder()
                    .email(request.getEmail())
                    .username(request.getUsername())
                    .role(Role.CLIENT)
                    .nom(request.getNom())
                    .telephone(request.getTelephone())
                    .ville(request.getVille())
                    .build();

            client.setHashedPassword(request.getPassword());

            user = clientRepository.save(client);
        }

        else if (request.getRole() == Role.DRIVER) {

            Chauffeur chauffeur = Chauffeur.builder()
                    .email(request.getEmail())
                    .username(request.getUsername())
                    .role(Role.DRIVER)
                    .nom(request.getNom())
                    .telephone(request.getTelephone())
                    .licenseType(request.getLicenseType())
                    .build();

            chauffeur.setHashedPassword(request.getPassword());

            user = chauffeurRepository.save(chauffeur);
        }

        else {

            User normalUser = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .role(request.getRole())
                    .build();

            normalUser.setHashedPassword(request.getPassword());

            user = userRepository.save(normalUser);
        }


        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getId()
        );

        return AuthResponse.builder()
                .token(token)
                .message("User registered successfully")
                .build();
    }


    public AuthResponse loginUser(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getId()
        );

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }
}