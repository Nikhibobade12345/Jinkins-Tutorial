package com.example.notification.service;

import com.example.notification.event.OrderCreatedEvent;
import com.example.notification.model.NotificationMessage;
import com.example.notification.model.OrderDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationService {
    private final RestTemplate restTemplate;
    private final String orderServiceBaseUrl;
    private final CopyOnWriteArrayList<NotificationMessage> notifications = new CopyOnWriteArrayList<>();

    public NotificationService(RestTemplate restTemplate,
                               @Value("${app.order-service.base-url}") String orderServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.orderServiceBaseUrl = orderServiceBaseUrl;
    }

    @KafkaListener(topics = "${app.kafka.order-created-topic:orders.created}", groupId = "notification-service")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        OrderDetails order = fetchOrderDetails(event.orderId());
        NotificationMessage notification = buildNotification(order);
        notifications.add(notification);
        System.out.println("NOTIFICATION: " + notification.message());
    }

    public OrderDetails fetchOrderDetails(String orderId) {
        return restTemplate.getForObject(orderServiceBaseUrl + "/api/orders/{id}", OrderDetails.class, orderId);
    }

    public NotificationMessage buildNotification(OrderDetails order) {
        return new NotificationMessage(order.id(),
                "Order " + order.id() + " confirmed for " + order.customerName()
                        + ": " + order.quantity() + " x " + order.item(),
                Instant.now());
    }

    public List<NotificationMessage> getAllNotifications() {
        return List.copyOf(notifications);
    }

    public int getNotificationCount() {
        return notifications.size();
    }
}
