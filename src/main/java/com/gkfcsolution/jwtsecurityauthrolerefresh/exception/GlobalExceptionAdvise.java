package com.gkfcsolution.jwtsecurityauthrolerefresh.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created on 2025 at 20:56
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 20:56
 */
@RestControllerAdvice
public class GlobalExceptionAdvise {

    @ExceptionHandler(ProductResourceNotFound.class)
    public String handleProductNotFoundException(){
        return "Product Not found";
    }
}
