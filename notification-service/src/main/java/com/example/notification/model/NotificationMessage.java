package com.example.notification.model;

import java.time.Instant;

public record NotificationMessage(String orderId, String message, Instant createdAt) {
}
