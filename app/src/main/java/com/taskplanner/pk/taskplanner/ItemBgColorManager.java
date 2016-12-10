package com.taskplanner.pk.taskplanner;

import android.content.Context;
import android.view.View;

public class ItemBgColorManager {

    public static View setBackgroundByCategory(View view, String category, Context context) {

        String color = CategoriesManager.getCategoryColor(category);

        view = setBackgroundByColorName(view, color, context);

        return view;
    }

    public static View setBackgroundByColorName(View view, String color, Context context) {

        String blue = context.getResources().getString(R.string.blue);
        String green = context.getResources().getString(R.string.green);
        String red = context.getResources().getString(R.string.red);
        String yellow = context.getResources().getString(R.string.yellow);
        String purple = context.getResources().getString(R.string.purple);
        String orange = context.getResources().getString(R.string.orange);

        if (color.equals(blue)) {
            view.setBackgroundResource(R.color.colorBlue100);
        } else if (color.equals(green)) {
            view.setBackgroundResource(R.color.colorGreen100);
        } else if (color.equals(red)) {
            view.setBackgroundResource(R.color.colorRed100);
        } else if (color.equals(yellow)) {
            view.setBackgroundResource(R.color.colorYellow100);
        } else if (color.equals(purple)) {
            view.setBackgroundResource(R.color.colorPurple100);
        } else if (color.equals(orange)) {
            view.setBackgroundResource(R.color.colorOrange100);
        } else {
            view.setBackgroundResource(R.color.colorGray100);
        }

        return view;
    }
}
