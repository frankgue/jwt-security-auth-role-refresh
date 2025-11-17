package com.gkfcsolution.jwtsecurityauthrolerefresh.service.impl;

import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.LoginRequest;
import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.RegisterRequest;
import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.TokenPair;
import com.gkfcsolution.jwtsecurityauthrolerefresh.entity.User;
import com.gkfcsolution.jwtsecurityauthrolerefresh.repository.UserRepository;
import com.gkfcsolution.jwtsecurityauthrolerefresh.service.AuthService;
import com.gkfcsolution.jwtsecurityauthrolerefresh.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2025 at 21:55
 * File: AuthServiceImpl.java.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 21:55
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            log.error("Username {} is already taken", registerRequest.getUsername());
            throw new IllegalArgumentException("Username is already taken");
        }

        // Create new user entity
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();

        // Save user to the database
        userRepository.save(user);
    }

    @Override
    public TokenPair login(LoginRequest loginRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Set authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate Token Pair
        return jwtService.generateTokenPair(authentication);
    }
}
