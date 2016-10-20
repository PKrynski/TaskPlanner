package com.taskplanner.pk.taskplanner;

public class Task {

    private String name;
    private String description;
    private String category;
    private boolean completed;

    public Task(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.completed = false;
    }

    public void setAsCompleted() {
        this.completed = true;
    }

    public void setAsIncomplete() {
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public boolean isCompleted() {
        return completed;
    }
}
