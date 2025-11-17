package com.gkfcsolution.jwtsecurityauthrolerefresh.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Created on 2025 at 11:09
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 17/11/2025
 * @time 11:09
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Intercept the request to extract and validate JWT token here
        // Do the validation and set authentication in the security context if valid
        // Authentication authentication = ...


        filterChain.doFilter(request, response);
    }
}
