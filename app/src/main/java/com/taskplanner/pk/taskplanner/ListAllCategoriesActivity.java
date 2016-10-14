package com.taskplanner.pk.taskplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAllCategoriesActivity extends AppCompatActivity {

    ArrayList<Category> myCategories = CategoriesManager.getMyCategories();

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loadAllCategories();
    }

    public void loadAllCategories() {

        ArrayAdapter<Category> adapter = new myCategoriesListAdapter();

        ListView list = (ListView) findViewById(R.id.categoriesListView);
        list.setAdapter(adapter);
    }

    private class myCategoriesListAdapter extends ArrayAdapter<Category> {

        public myCategoriesListAdapter() {
            super(ListAllCategoriesActivity.this, R.layout.task_item_view, myCategories);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.task_item_view, parent, false);
            }

            Category currentCategory = myCategories.get(position);

            String categoryName = currentCategory.getName();

            TextView nameTextView = (TextView) itemView.findViewById(R.id.item_textView_task_name);
            nameTextView.setText(currentCategory.getName());

            itemView = ItemBgColorManager.setBackgroundByCategory(itemView, categoryName);

            return itemView;
        }
    }

}
