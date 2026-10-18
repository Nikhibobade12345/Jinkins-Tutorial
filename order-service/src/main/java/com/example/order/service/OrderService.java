package com.example.order.service;

import com.example.order.event.OrderCreatedEvent;
import com.example.order.model.OrderRequest;
import com.example.order.model.OrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderService {
    private final ConcurrentHashMap<String, OrderResponse> orders = new ConcurrentHashMap<>();
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final String topic;

    public OrderService(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate,
                        @Value("${app.kafka.order-created-topic:orders.created}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public OrderResponse createOrder(OrderRequest request) {
        OrderResponse order = new OrderResponse(UUID.randomUUID().toString(), request.customerName(),
                request.item(), request.quantity(), Instant.now());
        orders.put(order.id(), order);
        OrderCreatedEvent event = new OrderCreatedEvent(order.id(), order.customerName(), order.item(),
                order.quantity(), order.createdAt());
        kafkaTemplate.send(topic, order.id(), event);
        return order;
    }

    public List<OrderResponse> getAllOrders() {
        return orders.values().stream().sorted((a, b) -> a.createdAt().compareTo(b.createdAt())).toList();
    }

    public OrderResponse getOrderById(String id) {
        OrderResponse order = orders.get(id);
        if (order == null) {
            throw new NoSuchElementException("Order not found: " + id);
        }
        return order;
    }

    public boolean orderExists(String id) {
        return orders.containsKey(id);
    }
}
