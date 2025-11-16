package com.gkfcsolution.jwtsecurityauthrolerefresh.service;

import com.gkfcsolution.jwtsecurityauthrolerefresh.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * Created on 2025 at 20:38
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 20:38
 */
public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product saveProduct(Product product);
    void deleteById(Long id);
}
