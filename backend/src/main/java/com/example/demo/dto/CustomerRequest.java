package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// ReubenVF: This request object defines the inbound payload for customer creation and update operations.
public record CustomerRequest(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Email is required") @Email(message = "Please provide a valid email") String email,
        @NotBlank(message = "Status is required") String status
) {
}
