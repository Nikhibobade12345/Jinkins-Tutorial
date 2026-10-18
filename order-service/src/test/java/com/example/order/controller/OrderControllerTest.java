package com.example.order.controller;

import com.example.order.model.OrderRequest;
import com.example.order.model.OrderResponse;
import com.example.order.service.OrderService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {
    @Test
    void createOrderDelegatesToService() {
        OrderService service = mock(OrderService.class);
        OrderController controller = new OrderController(service);
        OrderResponse expected = new OrderResponse("1", "Asha", "Book", 1, Instant.EPOCH);
        when(service.createOrder(any())).thenReturn(expected);
        assertEquals(expected, controller.createOrder(new OrderRequest("Asha", "Book", 1)));
    }

    @Test
    void getAllOrdersDelegatesToService() {
        OrderService service = mock(OrderService.class);
        OrderController controller = new OrderController(service);
        when(service.getAllOrders()).thenReturn(List.of());
        assertTrue(controller.getAllOrders().isEmpty());
        verify(service).getAllOrders();
    }

    @Test
    void getOrderByIdDelegatesToService() {
        OrderService service = mock(OrderService.class);
        OrderController controller = new OrderController(service);
        OrderResponse expected = new OrderResponse("1", "Asha", "Book", 1, Instant.EPOCH);
        when(service.getOrderById("1")).thenReturn(expected);
        assertEquals(expected, controller.getOrderById("1"));
    }
}
