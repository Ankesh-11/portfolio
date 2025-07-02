package com.portfolio.service.Impl;

import com.portfolio.config.MyDetailsService;
import com.portfolio.dto.AuthResponse;
import com.portfolio.dto.LoginRequest;
import com.portfolio.dto.RegisterRequest;
import com.portfolio.exception.UserFoundException;
import com.portfolio.model.User;
import com.portfolio.repository.UserRepository;
import com.portfolio.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final MyDetailsService userDetailsService;

    public void register(RegisterRequest request) throws UserFoundException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserFoundException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserFoundException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token ="";
        if(auth.isAuthenticated()) {
             token = jwtUtils.generateToken(request.getUsername());
        }
        return new AuthResponse(token);
    }
}
