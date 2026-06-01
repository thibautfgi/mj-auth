package com.mountain_journey.mj_auth.security;

import com.mountain_journey.mj_auth.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Log de traçabilité
        String path = request.getRequestURI();
        System.out.println("JWT FILTER: " + request.getMethod() + " " + path);
        // Laisse passer les requêtes preflight CORS et les routes publiques (auth, users)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()) ||
            path.equals("/api/auth/login") || path.equals("/api/auth/register") ||
            path.startsWith("/api/users")) {
            System.out.println("JWT FILTER: PASSE SANS AUTH " + path);
            filterChain.doFilter(request, response);
            return;
        }

        // Le reste de ton code actuel
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.isTokenValid(token)) {
                String email = jwtService.extractEmail(token);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(email, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
