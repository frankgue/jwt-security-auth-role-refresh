package com.gkfcsolution.jwtsecurityauthrolerefresh.service.impl;

import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.TokenPair;
import com.gkfcsolution.jwtsecurityauthrolerefresh.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;
    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshTokenExpirationMs;
    private static final String BEARER_PREFIX = "Bearer ";

    //Generate access token
    @Override
    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, jwtExpirationMs, new HashMap<>());
    }
    // Generate refresh token
    @Override
    public String generateRefreshToken(Authentication authentication) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "refresh");

       return generateToken(authentication, refreshTokenExpirationMs, claims);
    }



    // Validate token
    @Override
    public boolean valideTokenForUser(String token, UserDetails userDetails){

        final String username = extractUsernameFromToken(token);
       return username != null && username.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    // Validate if the token is refresh token
    public boolean isRefreshToken(String token){
        Claims claims = extractAllClaims(token);
        if (claims == null){
            return false;
        }
        return "refresh".equals(claims.get("tokenType"));
    }

    public String extractUsernameFromToken(String token) {

        Claims claims = extractAllClaims(token);

        if (claims != null){
            return claims.getSubject();
        }

        return null;
    }

    @Override
    public TokenPair generateTokenPair(Authentication authentication) {
        String accessToken = generateAccessToken(authentication);
        String refreshToken = generateRefreshToken(authentication);

        return new TokenPair(accessToken, refreshToken);
    }

    @Override
    public boolean isValidToken(String token) {
        return extractAllClaims(token) != null && isTokenExpired(token);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(Authentication authentication, long expirationTimeMs, Map<String, Object> extraClaims) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // Ajouter les rôles dans les claims
        extraClaims.put("roles",
                userPrincipal.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
        Date now = new Date(); // Time of token creation
        Date expiryDate = new Date(now.getTime() + expirationTimeMs); // Token expiration time
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .subject(userPrincipal.getUsername())
                .claims(extraClaims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return !extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e){
            log.error("Could not extract claims from token: {}", e.getMessage());
            throw new RuntimeException("Could not extract claims from token", e);
        }

        return claims;
    }


}
