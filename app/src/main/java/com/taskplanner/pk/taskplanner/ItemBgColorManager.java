package com.taskplanner.pk.taskplanner;

import android.view.View;

public class ItemBgColorManager {

    public static View setBackgroundByCategory(View view, String category) {

        String color = CategoriesManager.getCategoryColor(category);

        switch (color){
            case "blue":
                view.setBackgroundResource(R.color.colorBlue200);
                break;
            case "green":
                view.setBackgroundResource(R.color.colorGreen200);
                break;
            case "red":
                view.setBackgroundResource(R.color.colorRed200);
                break;
            case "yellow":
                view.setBackgroundResource(R.color.colorYellow200);
                break;
            case "purple":
                view.setBackgroundResource(R.color.colorPurple200);
                break;
            case "orange":
                view.setBackgroundResource(R.color.colorOrange200);
                break;
            default:
                break;
        }

        return view;
    }
}
