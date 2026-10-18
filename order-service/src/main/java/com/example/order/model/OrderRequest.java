package com.example.order.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record OrderRequest(
        @NotBlank String customerName,
        @NotBlank String item,
        @Min(1) int quantity) {
}
