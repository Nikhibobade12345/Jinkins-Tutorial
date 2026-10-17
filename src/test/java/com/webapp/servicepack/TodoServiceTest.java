
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
    void testGetAllTodosInitiallyEmpty() {
        List<TodoItem> todos = todoService.getAllTodos();

        assertNotNull(todos);
        assertTrue(todos.isEmpty());
    }

    @Test
    void testAddTodoCreatesAndStoresTodo() {
        todoService.addTodo("Write unit tests");

        List<TodoItem> todos = todoService.getAllTodos();

        assertEquals(1, todos.size());
        assertNotNull(todos.get(0).getId());
        assertFalse(todos.get(0).getId().isBlank());
        assertEquals("Write unit tests", todos.get(0).getDescription());
        assertFalse(todos.get(0).isCompleted());
    }

    @Test
    void testAddMultipleTodosPreservesOrder() {
        todoService.addTodo("First task");
        todoService.addTodo("Second task");

        List<TodoItem> todos = todoService.getAllTodos();

        assertEquals(2, todos.size());
        assertEquals("First task", todos.get(0).getDescription());
        assertEquals("Second task", todos.get(1).getDescription());
    }

    @Test
    void testAddTodoTrimsDescription() {
        todoService.addTodo("  Learn Spring Boot  ");

        assertEquals(
                "Learn Spring Boot",
                todoService.getAllTodos().get(0).getDescription()
        );
    }

    @Test
    void testAddTodoRejectsEmptyDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> todoService.addTodo("   ")
        );

        assertTrue(todoService.getAllTodos().isEmpty());
    }

    @Test
    void testAddTodoRejectsNullDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> todoService.addTodo(null)
        );
    }

    @Test
    void testUpdateTodoChangesDescription() {
        todoService.addTodo("Old task");

        TodoItem item = todoService.getAllTodos().get(0);

        todoService.updateTodo(item.getId(), "Updated task");

        assertEquals("Updated task", item.getDescription());
        assertEquals(1, todoService.getAllTodos().size());
    }

    @Test
    void testUpdateTodoRejectsNonExistentId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> todoService.updateTodo("invalid-id", "Updated task")
        );
    }

    @Test
    void testUpdateTodoRejectsEmptyDescription() {
        todoService.addTodo("Original task");
        TodoItem item = todoService.getAllTodos().get(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> todoService.updateTodo(item.getId(), "")
        );

        assertEquals("Original task", item.getDescription());
    }

    @Test
    void testToggleTodoMarksAsCompleted() {
        todoService.addTodo("Complete assignment");
        TodoItem item = todoService.getAllTodos().get(0);

        todoService.toggleTodo(item.getId());

        assertTrue(item.isCompleted());
    }

    @Test
    void testToggleTodoTwiceMarksAsPending() {
        todoService.addTodo("Complete assignment");
        TodoItem item = todoService.getAllTodos().get(0);

        todoService.toggleTodo(item.getId());
        todoService.toggleTodo(item.getId());

        assertFalse(item.isCompleted());
    }

    @Test
    void testToggleTodoRejectsNonExistentId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> todoService.toggleTodo("invalid-id")
        );
    }

    @Test
    void testDeleteTodoRemovesTargetTodo() {
        todoService.addTodo("Task to delete");

        String id = todoService.getAllTodos().get(0).getId();

        todoService.deleteTodo(id);

        assertTrue(todoService.getAllTodos().isEmpty());
    }

    @Test
    void testDeleteTodoWithNonExistentIdPreservesExistingTodos() {
        todoService.addTodo("Task to keep");

        todoService.deleteTodo("non-existent-id");

        List<TodoItem> todos = todoService.getAllTodos();

        assertEquals(1, todos.size());
        assertEquals("Task to keep", todos.get(0).getDescription());
    }
}
