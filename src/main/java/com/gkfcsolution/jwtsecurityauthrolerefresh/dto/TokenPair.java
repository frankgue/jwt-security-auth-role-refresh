package com.gkfcsolution.jwtsecurityauthrolerefresh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2025 at 12:44
 * File: TokenPair.java.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 17/11/2025
 * @time 12:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenPair {
    private String accessToken;
    private String refreshToken;
}
