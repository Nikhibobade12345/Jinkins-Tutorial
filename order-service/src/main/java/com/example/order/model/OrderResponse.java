package com.example.order.model;

import java.time.Instant;

public record OrderResponse(String id, String customerName, String item, int quantity, Instant createdAt) {
}
