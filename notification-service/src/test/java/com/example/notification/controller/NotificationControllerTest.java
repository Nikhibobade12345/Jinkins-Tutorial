package com.example.notification.controller;

import com.example.notification.model.NotificationMessage;
import com.example.notification.service.NotificationService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationControllerTest {
    @Test
    void getAllNotificationsDelegatesToService() {
        NotificationService service = mock(NotificationService.class);
        NotificationController controller = new NotificationController(service);
        when(service.getAllNotifications()).thenReturn(List.of(new NotificationMessage("1", "sent", Instant.EPOCH)));
        assertEquals(1, controller.getAllNotifications().size());
    }

    @Test
    void getNotificationCountDelegatesToService() {
        NotificationService service = mock(NotificationService.class);
        NotificationController controller = new NotificationController(service);
        when(service.getNotificationCount()).thenReturn(3);
        assertEquals(3, controller.getNotificationCount());
    }
}
