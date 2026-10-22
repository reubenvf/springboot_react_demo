package com.example.demo.mapper;

import com.example.demo.dto.ProductResponse;
import com.example.demo.model.Product;

// ReubenVF: Keeps entity-to-response conversion in one dedicated mapper for cleaner service logic.
public final class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
