package com.example.notification.event;

import java.time.Instant;

public record OrderCreatedEvent(String orderId, String customerName, String item, int quantity, Instant createdAt) {
}
