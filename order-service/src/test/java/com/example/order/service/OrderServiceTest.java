package com.example.order.service;

import com.example.order.event.OrderCreatedEvent;
import com.example.order.model.OrderRequest;
import com.example.order.model.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private OrderService service;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        service = new OrderService(kafkaTemplate, "orders.created");
    }

    @Test
    void createOrderStoresOrderAndPublishesEvent() {
        OrderResponse order = service.createOrder(new OrderRequest("Asha", "Keyboard", 2));
        assertNotNull(order.id());
        assertEquals("Asha", order.customerName());
        assertEquals(1, service.getAllOrders().size());
        verify(kafkaTemplate).send(eq("orders.created"), eq(order.id()), any(OrderCreatedEvent.class));
    }

    @Test
    void getAllOrdersReturnsCreatedOrders() {
        service.createOrder(new OrderRequest("Asha", "Mouse", 1));
        service.createOrder(new OrderRequest("Ravi", "Keyboard", 1));
        assertEquals(2, service.getAllOrders().size());
    }

    @Test
    void getOrderByIdReturnsMatchingOrder() {
        OrderResponse created = service.createOrder(new OrderRequest("Asha", "Mouse", 1));
        assertEquals(created, service.getOrderById(created.id()));
    }

    @Test
    void getOrderByIdThrowsWhenMissing() {
        assertThrows(NoSuchElementException.class, () -> service.getOrderById("missing"));
    }

    @Test
    void orderExistsReflectsStoreState() {
        OrderResponse created = service.createOrder(new OrderRequest("Asha", "Mouse", 1));
        assertTrue(service.orderExists(created.id()));
        assertFalse(service.orderExists("missing"));
    }
}
