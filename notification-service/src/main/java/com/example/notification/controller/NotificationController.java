package com.example.notification.controller;

import com.example.notification.model.NotificationMessage;
import com.example.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationMessage> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/count")
    public int getNotificationCount() {
        return notificationService.getNotificationCount();
    }
}
