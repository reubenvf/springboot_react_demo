package com.example.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// ReubenVF: This DTO defines the product payload accepted by the API for creation and updates.
public record ProductRequest(
        @NotBlank(message = "Product name is required") String name,
        String description,
        @NotNull(message = "Quantity is required") @Min(value = 0, message = "Quantity cannot be less than 0") Integer quantity,
        @NotNull(message = "Price is required") @DecimalMin(value = "0.00", inclusive = true, message = "Price cannot be negative") BigDecimal price
) {
}
