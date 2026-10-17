package com.webapp.model;

public class TodoItem {

    private String id;
    private String description;
    private boolean completed;

    public TodoItem(String id, String description) {
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}