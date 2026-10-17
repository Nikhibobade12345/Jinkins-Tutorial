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
        String id = UUID.randomUUID().toString();
        todoMap.put(id, new TodoItem(id, description));
    }

    public void deleteTodo(String id) {
        todoMap.remove(id);
    }
}
