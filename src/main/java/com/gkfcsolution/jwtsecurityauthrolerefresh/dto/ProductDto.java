package com.gkfcsolution.jwtsecurityauthrolerefresh.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created on 2025 at 21:43
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 21:43
 */
@Data
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

}
