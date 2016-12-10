package com.taskplanner.pk.taskplanner;

import android.view.View;

public class ItemBgColorManager {

    public static View setBackgroundByCategory(View view, String category) {

        int color = CategoriesManager.getCategoryColor(category);

        view = setBackgroundByColorName(view, color);

        return view;
    }

    public static View setBackgroundByColorName(View view, int color) {


        switch (color){
            case 0:
                view.setBackgroundResource(R.color.colorBlue100);
                break;
            case 1:
                view.setBackgroundResource(R.color.colorRed100);
                break;
            case 2:
                view.setBackgroundResource(R.color.colorGreen100);
                break;
            case 3:
                view.setBackgroundResource(R.color.colorYellow100);
                break;
            case 4:
                view.setBackgroundResource(R.color.colorOrange100);
                break;
            case 5:
                view.setBackgroundResource(R.color.colorPurple100);
                break;
            default:
                view.setBackgroundResource(R.color.colorGray100);
                break;
        }

        return view;
    }
}
