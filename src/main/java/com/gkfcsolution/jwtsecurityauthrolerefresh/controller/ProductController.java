package com.gkfcsolution.jwtsecurityauthrolerefresh.controller;

import com.gkfcsolution.jwtsecurityauthrolerefresh.entity.Product;
import com.gkfcsolution.jwtsecurityauthrolerefresh.exception.ProductResourceNotFound;
import com.gkfcsolution.jwtsecurityauthrolerefresh.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2025 at 20:45
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 20:45
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveProduct(@Valid @RequestBody Product product){
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product){
        Product existingProduct = productService.getProductById(id).orElseThrow(() -> new ProductResourceNotFound("Product with id " + id + " not found"));
        if(existingProduct == null){
            throw new ProductResourceNotFound("Product with id " + id + " not found");
        }
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        Product updatedProduct = productService.updateProduct(existingProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        Product existingProduct = productService.getProductById(id).orElseThrow(() -> new ProductResourceNotFound("Product with id " + id + " not found"));
        if(existingProduct == null){
            throw new ProductResourceNotFound("Product with id " + id + " not found");
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
