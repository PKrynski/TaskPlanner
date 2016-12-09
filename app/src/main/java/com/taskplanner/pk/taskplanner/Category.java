package com.taskplanner.pk.taskplanner;

public class Category {

    private String name;
    private String color;

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void update(String newColor) {
        this.color = newColor;
    }
}
