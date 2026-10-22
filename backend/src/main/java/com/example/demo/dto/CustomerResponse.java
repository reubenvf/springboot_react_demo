package com.example.demo.dto;

import java.time.LocalDateTime;

// ReubenVF: This response DTO exposes the customer fields that are safe and useful to return to the client.
public record CustomerResponse(
        Long id,
        String name,
        String email,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
