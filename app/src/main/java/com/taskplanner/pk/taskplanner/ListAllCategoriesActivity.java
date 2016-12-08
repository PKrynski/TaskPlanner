package com.taskplanner.pk.taskplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListAllCategoriesActivity extends AppCompatActivity {

    ArrayList<Category> myCategories = CategoriesManager.getMyCategories();
    ArrayList<Category> currentCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runNewCategoryActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadAllCategories();
    }

    public void loadAllCategories() {

        currentCategories = getCategoriesFromSharedPreferences();

        ArrayAdapter<Category> adapter = new myCategoriesListAdapter();

        ListView list = (ListView) findViewById(R.id.categoriesListView);
        list.setAdapter(adapter);
    }

    private class myCategoriesListAdapter extends ArrayAdapter<Category> {

        public myCategoriesListAdapter() {
            super(ListAllCategoriesActivity.this, R.layout.category_item_view, currentCategories);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.category_item_view, parent, false);
            }

            Category currentCategory = currentCategories.get(position);

            String categoryName = currentCategory.getName();

            TextView nameTextView = (TextView) itemView.findViewById(R.id.item_textView_category_name);
            nameTextView.setText(currentCategory.getName());

            itemView = ItemBgColorManager.setBackgroundByCategory(itemView, categoryName);

            return itemView;
        }
    }

    public void runNewCategoryActivity() {
        Intent intent = new Intent(this, NewCategoryActivity.class);
        startActivity(intent);
    }

    private ArrayList<Category> getCategoriesFromSharedPreferences() {

        SharedPreferences prefs = getSharedPreferences("CategoriesPrefs", MODE_PRIVATE);
        String categories = prefs.getString("categories", "None");

        if (!"None".equals(categories)) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Category>>(){}.getType();
            ArrayList<Category> readCategories = gson.fromJson(categories, type);
            CategoriesManager.setMyCategories(readCategories);

            return readCategories;
        }
        return myCategories;
    }

}
