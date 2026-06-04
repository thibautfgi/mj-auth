package com.mountain_journey.mj_auth.service;

import com.mountain_journey.mj_auth.dto.*;
import com.mountain_journey.mj_auth.entity.User;
import com.mountain_journey.mj_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUserEmail(request.getUserEmail())) {
            // ✅ 409 Conflict au lieu de 500
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
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
                // ✅ 401 Unauthorized au lieu de 500
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Identifiants incorrects"));

        if (!passwordEncoder.matches(request.getUserPassword(), user.getUserPassword())) {
            // ✅ Message générique volontaire (évite l'énumération d'emails)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Identifiants incorrects");
        }

        String token = jwtService.generateToken(user.getUserEmail());
        return new AuthResponse(token, user.getUserEmail(), user.getUserFirstName(), user.getUserLastName());
    }

    public WhoiamResponse whoiam(String email) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));
        return new WhoiamResponse(user.getUserEmail(), user.getUserFirstName(), user.getUserLastName());
    }
}
