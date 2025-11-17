package com.gkfcsolution.jwtsecurityauthrolerefresh.service;

import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.LoginRequest;
import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.RegisterRequest;
import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.TokenPair;

/**
 * Created on 2025 at 21:55
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 21:55
 */
public interface AuthService {
    void registerUser(RegisterRequest registerRequest);
    TokenPair login(LoginRequest loginRequest);
}
