package com.taskplanner.pk.taskplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListAllCategoriesActivity extends AppCompatActivity {

    ArrayList<Category> currentCategories;
    ArrayAdapter<Category> adapter;

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
        registerCategoryItemClicks();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.categories_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int id = item.getItemId();

        if( id == R.id.edit) {
            editCategory(info.position);
            return true;
        } else if( id == R.id.delete) {
            deleteCategory(info.position);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void registerCategoryItemClicks() {

        ListView list = (ListView) findViewById(R.id.categoriesListView);

        registerForContextMenu(list);
    }

    public void loadAllCategories() {

        currentCategories = getCategoriesFromSharedPreferences();

        adapter = new myCategoriesListAdapter();

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
        return CategoriesManager.getMyCategories();
    }

    private void editCategory(int position) {
        Intent intent = new Intent(this, EditCategoryActivity.class);
        intent.putExtra("categoryPosition", position);
        startActivity(intent);
    }

    private void deleteCategory(int position) {

        ListView list = (ListView) findViewById(R.id.categoriesListView);
        final Category selectedCategory = currentCategories.get(position);

        int firstVisiblePosition = list.getFirstVisiblePosition();

        Animation anim = AnimationUtils.loadAnimation(ListAllCategoriesActivity.this, R.anim.move_right);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                currentCategories.remove(selectedCategory);
                adapter.notifyDataSetChanged();
                saveCategoriesInSharedPreferences();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        int indexToBeAnimated = position - firstVisiblePosition;
        list.getChildAt(indexToBeAnimated).startAnimation(anim);
    }

    private void saveCategoriesInSharedPreferences() {

        ArrayList<Category> myCategories = CategoriesManager.getMyCategories();

        SharedPreferences prefs = getSharedPreferences("CategoriesPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String categories = gson.toJson(myCategories);

        editor.putString("categories", categories);
        editor.apply();
    }

}
