package com.taskplanner.pk.taskplanner;

import java.util.ArrayList;

public class CategoriesManager {

    public static ArrayList<Category> categories = new ArrayList<>();

    public void addNewCategory(String name) {
        Category my_category = new Category(name);
        categories.add(my_category);
    }

    public void addNewCategory(String name, String color) {
        Category my_category = new Category(name,color);
        categories.add(my_category);
    }

    public ArrayList<Category> getAllCategories() {
        return categories;
    }

    private class Category {

        public String name;
        public String color;

        public Category(String name) {
            this.name = name;
        }

        public Category(String name, String color) {
            this.name = name;
            this.color = color;
        }
    }


}
