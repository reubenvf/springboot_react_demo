package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// ReubenVF: This response DTO returns only the fields the frontend needs for display and editing.
public record ProductResponse(
        Long id,
        String name,
        String description,
        Integer quantity,
        BigDecimal price,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
