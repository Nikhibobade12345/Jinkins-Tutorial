
package com.webapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemTest {

    @Test
    void testParameterizedConstructorAndGetters() {
        TodoItem item = new TodoItem("1", "Buy groceries");

        assertEquals("1", item.getId());
        assertEquals("Buy groceries", item.getDescription());
        assertFalse(item.isCompleted());
    }

    @Test
    void testSetDescription() {
        TodoItem item = new TodoItem("1", "Buy groceries");

        item.setDescription("Read a book");

        assertEquals("Read a book", item.getDescription());
    }

    @Test
    void testSetCompleted() {
        TodoItem item = new TodoItem("1", "Buy groceries");

        item.setCompleted(true);

        assertTrue(item.isCompleted());
    }

    @Test
    void testSetCompletedBackToFalse() {
        TodoItem item = new TodoItem("1", "Buy groceries");
        item.setCompleted(true);

        item.setCompleted(false);

        assertFalse(item.isCompleted());
    }
}
