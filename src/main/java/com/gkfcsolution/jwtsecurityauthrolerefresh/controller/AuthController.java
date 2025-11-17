package com.gkfcsolution.jwtsecurityauthrolerefresh.controller;

import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.LoginRequest;
import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.RefreshTokenRequest;
import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.RegisterRequest;
import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.TokenPair;
import com.gkfcsolution.jwtsecurityauthrolerefresh.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2025 at 21:47
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 21:47
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request){
        // Save the user using the AuthService
        authService.registerUser(request);
        log.info("User {} registered successfully", request.getUsername());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        TokenPair tokenPair = authService.login(loginRequest);

        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        TokenPair tokenPair = authService.refreshToken(request);
        return ResponseEntity.ok(tokenPair);
    }

}
