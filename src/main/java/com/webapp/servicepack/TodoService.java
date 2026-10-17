package com.webapp.servicepack;

import com.webapp.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoService {

    private final Map<String, TodoItem> todoMap = new LinkedHashMap<>();

    public List<TodoItem> getAllTodos() {
        return new ArrayList<>(todoMap.values());
    }

    public void addTodo(String description) {
        validateDescription(description);

        String id = UUID.randomUUID().toString();
        todoMap.put(id, new TodoItem(id, description.trim()));
    }

    public void updateTodo(String id, String description) {
        validateDescription(description);

        TodoItem todo = todoMap.get(id);

        if (todo == null) {
            throw new IllegalArgumentException("Todo not found: " + id);
        }

        todo.setDescription(description.trim());
    }

    public void toggleTodo(String id) {
        TodoItem todo = todoMap.get(id);

        if (todo == null) {
            throw new IllegalArgumentException("Todo not found: " + id);
        }

        todo.setCompleted(!todo.isCompleted());
    }

    public void deleteTodo(String id) {
        todoMap.remove(id);
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Todo description cannot be empty"
            );
        }
    }
}