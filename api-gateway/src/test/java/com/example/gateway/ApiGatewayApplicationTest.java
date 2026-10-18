package com.example.gateway;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ApiGatewayApplicationTest {
    @Test
    void applicationClassCanBeInstantiated() {
        assertDoesNotThrow(ApiGatewayApplication::new);
    }
}
