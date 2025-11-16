package com.gkfcsolution.jwtsecurityauthrolerefresh.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created on 2025 at 20:34
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 20:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

}
