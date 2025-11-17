package com.gkfcsolution.jwtsecurityauthrolerefresh.service;

import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.TokenPair;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created on 2025 at 10:59
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 17/11/2025
 * @time 10:59
 */
public interface JwtService {
    String generateAccessToken(Authentication authentication);
    String generateRefreshToken(Authentication authentication);
    boolean isRefreshToken(String token);
    String extractUsernameFromToken(String token);
    TokenPair generateTokenPair(Authentication authentication);

    boolean valideTokenForUser(String token, UserDetails userDetails);
    boolean isValidToken(String token);
}
