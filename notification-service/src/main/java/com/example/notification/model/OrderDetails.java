package com.example.notification.model;

import java.time.Instant;

public record OrderDetails(String id, String customerName, String item, int quantity, Instant createdAt) {
}
