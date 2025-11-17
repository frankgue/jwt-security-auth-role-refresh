package com.gkfcsolution.jwtsecurityauthrolerefresh.service.impl;

import com.gkfcsolution.jwtsecurityauthrolerefresh.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;
    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;
    private static final String BEARER_PREFIX = "Bearer ";

    //Generate access token
    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, jwtExpirationMs, new HashMap<>());
    }
    // Generate refresh token
    public String generateRefreshToken(Authentication authentication) {

        Map<String, String> claims = new HashMap<>();
        claims.put("tokenType", "refresh");

       return generateToken(authentication, refreshTokenExpirationMs, claims);
    }
    // Validate token
    public boolean isValidToken(String token, UserDetails userDetails){

        final String username = extractUsernameFromToken(token);
        if (!username.equals(userDetails.getUsername())) {
            return false;
        }

        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (SignatureException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    // Validate if the token is refresh token
    public boolean isRefreshToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return "refresh".equals(claims.get("tokenType"));
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(Authentication authentication, long expirationTimeMs, Map<String, String> extraClaims) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Date now = new Date(); // Time of token creation
        Date expiryDate = new Date(now.getTime() + expirationTimeMs); // Token expiration time
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claims(extraClaims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    private String extractUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
