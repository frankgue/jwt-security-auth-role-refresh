package com.gkfcsolution.jwtsecurityauthrolerefresh.service.impl;

import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.RegisterRequest;
import com.gkfcsolution.jwtsecurityauthrolerefresh.entity.User;
import com.gkfcsolution.jwtsecurityauthrolerefresh.repository.UserRepository;
import com.gkfcsolution.jwtsecurityauthrolerefresh.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
