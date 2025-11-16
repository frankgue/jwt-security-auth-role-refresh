package com.gkfcsolution.jwtsecurityauthrolerefresh.mapper;

import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.ProductDto;
import com.gkfcsolution.jwtsecurityauthrolerefresh.dto.ProductRequest;
import com.gkfcsolution.jwtsecurityauthrolerefresh.entity.Product;
import org.springframework.stereotype.Component;

/**
 * Created on 2025 at 21:41
 * File: null.java
 * Project: jwt-security-auth-role-refresh
 *
 * @author Frank GUEKENG
 * @date 16/11/2025
 * @time 21:41
 */
@Component
public class ProductMapper {

    public Product toEntity(ProductRequest dto) {
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }

    public ProductDto toDto(Product entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }
}
