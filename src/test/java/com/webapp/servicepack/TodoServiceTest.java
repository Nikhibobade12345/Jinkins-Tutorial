package com.webapp.servicepack;

import com.webapp.model.TodoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoServiceTest {

    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoService();
    }

    @Test
    void testGetAllTodos_initiallyEmpty() {
        List<TodoItem> todos = todoService.getAllTodos();

        assertNotNull(todos);
        assertTrue(todos.isEmpty());
    }

    @Test
    void testAddTodo_createsAndStoresTodo() {
        todoService.addTodo("Write unit tests");

        List<TodoItem> todos = todoService.getAllTodos();
        assertEquals(1, todos.size());

        TodoItem item = todos.get(0);
        assertNotNull(item.getId());
        assertFalse(item.getId().isBlank());
        assertEquals("Write unit tests", item.getDescription());
    }

    @Test
    void testAddMultipleTodos_preservesOrder() {
        todoService.addTodo("First task");
        todoService.addTodo("Second task");

        List<TodoItem> todos = todoService.getAllTodos();
        assertEquals(2, todos.size());
        assertEquals("First task", todos.get(0).getDescription());
        assertEquals("Second task", todos.get(1).getDescription());
    }

    @Test
    void testDeleteTodo_removesTargetTodo() {
        todoService.addTodo("Task to delete");
        List<TodoItem> todos = todoService.getAllTodos();
        assertEquals(1, todos.size());
        String idToDelete = todos.get(0).getId();

        todoService.deleteTodo(idToDelete);

        List<TodoItem> afterDelete = todoService.getAllTodos();
        assertTrue(afterDelete.isEmpty());
    }

    @Test
    void testDeleteTodo_withNonExistentId_doesNotAffectExistingTodos() {
        todoService.addTodo("Task to keep");
        assertEquals(1, todoService.getAllTodos().size());

        todoService.deleteTodo("non-existent-id");

        List<TodoItem> todos = todoService.getAllTodos();
        assertEquals(1, todos.size());
        assertEquals("Task to keep", todos.get(0).getDescription());
    }
}
