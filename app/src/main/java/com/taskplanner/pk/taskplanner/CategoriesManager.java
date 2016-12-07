package com.taskplanner.pk.taskplanner;

import java.util.ArrayList;

public class CategoriesManager {

    private static ArrayList<Category> categoriesArrayList = new ArrayList<>();

    public static void addNewCategory(String name, String color) {

        Category my_category = new Category(name, color);
        categoriesArrayList.add(my_category);
    }

    public static ArrayList<Category> getMyCategories() {
        return categoriesArrayList;
    }

    public static void setMyCategories(ArrayList<Category> modifiedCategories) {
        categoriesArrayList = modifiedCategories;
    }

    public static ArrayList<String> getCategoriesNames() {

        ArrayList<String> categoriesNames = new ArrayList<>();

        for( Category current_category : categoriesArrayList) {
            categoriesNames.add(current_category.getName());
        }

        return categoriesNames;
    }

    public static String getCategoryColor(String categoryName) {

        for( Category current_category : categoriesArrayList) {

            if( categoryName.equals(current_category.getName()) ) {
                return current_category.getColor();
            }
        }

        return "default";
    }

}
