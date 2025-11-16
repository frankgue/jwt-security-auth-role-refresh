package com.gkfcsolution.jwtsecurityauthrolerefresh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created on 2025 at 20:54
 * File: ProductResourceNotFound.java.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 20:54
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductResourceNotFound extends RuntimeException{
    public ProductResourceNotFound(String message) {
    }
}
