package com.webapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemTest {

    @Test
    void testParameterizedConstructorAndGetters() {
        TodoItem item = new TodoItem("1", "Buy groceries");

        assertEquals("1", item.getId());
        assertEquals("Buy groceries", item.getDescription());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        TodoItem item = new TodoItem();
        item.setId("2");
        item.setDescription("Read a book");

        assertEquals("2", item.getId());
        assertEquals("Read a book", item.getDescription());
    }
}
