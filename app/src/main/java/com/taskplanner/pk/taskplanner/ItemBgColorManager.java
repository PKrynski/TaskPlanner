package com.taskplanner.pk.taskplanner;

import android.view.View;

public class ItemBgColorManager {

    public static View setBackgroundByCategory(View view, String category) {

        String color = CategoriesManager.getCategoryColor(category);

        switch (color){
            case "blue":
                view.setBackgroundResource(R.color.colorBlue100);
                break;
            case "green":
                view.setBackgroundResource(R.color.colorGreen100);
                break;
            case "red":
                view.setBackgroundResource(R.color.colorRed100);
                break;
            case "yellow":
                view.setBackgroundResource(R.color.colorYellow100);
                break;
            case "purple":
                view.setBackgroundResource(R.color.colorPurple100);
                break;
            case "orange":
                view.setBackgroundResource(R.color.colorOrange100);
                break;
            default:
                break;
        }

        return view;
    }
}
