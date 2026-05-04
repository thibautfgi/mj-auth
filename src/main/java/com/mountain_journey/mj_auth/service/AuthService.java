package com.mountain_journey.mj_auth.service;

import com.mountain_journey.mj_auth.dto.*;
import com.mountain_journey.mj_auth.entity.User;
import com.mountain_journey.mj_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUserEmail(request.getUserEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = User.builder()
                .userFirstName(request.getUserFirstName())
                .userLastName(request.getUserLastName())
                .userPhone(request.getUserPhone())
                .userEmail(request.getUserEmail())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user.getUserEmail());
        return new AuthResponse(token, user.getUserEmail(), user.getUserFirstName(), user.getUserLastName());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (!passwordEncoder.matches(request.getUserPassword(), user.getUserPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        String token = jwtService.generateToken(user.getUserEmail());
        return new AuthResponse(token, user.getUserEmail(), user.getUserFirstName(), user.getUserLastName());
    }
}