package com.gkfcsolution.jwtsecurityauthrolerefresh.service.impl;

import com.gkfcsolution.jwtsecurityauthrolerefresh.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created on 2025 at 10:59
 * File: JwtServiceImpl.java.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 17/11/2025
 * @time 10:59
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;
    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;
    private static final String BEARER_PREFIX = "Bearer ";

    //Generate access token
    // Generate refresh token
    // Validate token
    // Extract username from token
    // Validate if the token is refresh token
}
