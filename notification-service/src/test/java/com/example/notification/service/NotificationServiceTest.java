package com.example.notification.service;

import com.example.notification.event.OrderCreatedEvent;
import com.example.notification.model.NotificationMessage;
import com.example.notification.model.OrderDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {
    private RestTemplate restTemplate;
    private NotificationService service;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        service = new NotificationService(restTemplate, "http://localhost:8081");
    }

    @Test
    void buildNotificationFormatsOrderMessage() {
        OrderDetails order = new OrderDetails("o1", "Asha", "Book", 2, Instant.EPOCH);
        NotificationMessage message = service.buildNotification(order);
        assertEquals("o1", message.orderId());
        assertTrue(message.message().contains("2 x Book"));
        assertTrue(message.message().contains("Asha"));
        assertNotNull(message.createdAt());
    }

    @Test
    void getAllNotificationsStartsEmpty() {
        assertTrue(service.getAllNotifications().isEmpty());
        assertEquals(0, service.getNotificationCount());
    }

    @Test
    void consumeOrderCreatedFetchesOrderAndStoresNotification() {
        OrderDetails details = new OrderDetails("o1", "Asha", "Book", 2, Instant.EPOCH);
        when(restTemplate.getForObject(
                eq("http://localhost:8081/api/orders/{id}"), eq(OrderDetails.class), eq("o1")))
                .thenReturn(details);

        service.consumeOrderCreated(new OrderCreatedEvent("o1", "Asha", "Book", 2, Instant.EPOCH));

        assertEquals(1, service.getNotificationCount());
        assertEquals("o1", service.getAllNotifications().get(0).orderId());
        verify(restTemplate).getForObject(
                "http://localhost:8081/api/orders/{id}", OrderDetails.class, "o1");
    }

    @Test
    void fetchOrderDetailsCallsOrderServiceUsingRestTemplate() {
        OrderDetails expected = new OrderDetails("o1", "Asha", "Book", 2, Instant.EPOCH);
        when(restTemplate.getForObject(
                "http://localhost:8081/api/orders/{id}", OrderDetails.class, "o1"))
                .thenReturn(expected);

        assertEquals(expected, service.fetchOrderDetails("o1"));
    }

    @Test
    void getAllNotificationsReturnsSnapshot() {
        OrderDetails details = new OrderDetails("o1", "Asha", "Book", 1, Instant.EPOCH);
        when(restTemplate.getForObject(anyString(), eq(OrderDetails.class), anyString())).thenReturn(details);
        service.consumeOrderCreated(new OrderCreatedEvent("o1", "Asha", "Book", 1, Instant.EPOCH));
        var snapshot = service.getAllNotifications();
        assertEquals(1, snapshot.size());
        assertEquals(1, service.getNotificationCount());
    }
}
