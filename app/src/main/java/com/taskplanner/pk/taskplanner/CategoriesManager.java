package com.taskplanner.pk.taskplanner;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesManager {

    public static HashMap<String, Category> categories = new HashMap<>();
    public static ArrayList<String> categoriesArrayList = new ArrayList<>();

    public static void addNewCategory(String name, String color) {

        Category my_category = new Category(name, color);
        categories.put(name, my_category);
        categoriesArrayList.add(name);
    }

    public HashMap<String, Category> getAllCategories() {

        return categories;
    }

    public static String getCategoryColor(String categoryName) {

        String color;

        try {
            color = categories.get(categoryName).getColor();
        } catch (NullPointerException e) {
            color = "default";
        }

        return color;
    }

    public static void setupDefaultCategories() {

        addNewCategory("Uncategorized", "default");
        addNewCategory("Home", "orange");
        addNewCategory("Work", "blue");
        addNewCategory("Sport","green");
        addNewCategory("Travel","purple");
    }




    private static class Category {

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
    }
}
