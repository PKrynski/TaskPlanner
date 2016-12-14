package com.taskplanner.pk.taskplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditCategoryActivity extends AppCompatActivity {

    private String categoryColor;
    private List<String> colors;
    int categoryPosition;
    Category currentCategory;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.yes_no_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_accept) {
            updateCategory();
        } else if (id == R.id.action_decline) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        colors = Arrays.asList(getResources().getStringArray(R.array.colors_array));

        Intent intent = getIntent();
        categoryPosition = intent.getIntExtra("categoryPosition", 0);
        fillCategoryDetails();
        makeColorSpinner();
    }

    public void fillCategoryDetails() {

        currentCategory = CategoriesManager.getMyCategories().get(categoryPosition);

        EditText categoryName = (EditText) findViewById(R.id.editTextCategoryName);
        categoryName.setText(currentCategory.getName());
        categoryName.setFocusable(false);
    }

    private void updateCategory() {

        List<String> colors = Arrays.asList(getResources().getStringArray(R.array.colors_array));
        int colorId = colors.indexOf(categoryColor);

        currentCategory.update(colorId);

        saveCategoriesInSharedPreferences();
        finish();
    }

    public void makeColorSpinner() {

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerColorSelect);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setSelection(currentCategory.getColor());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String picked_color = colors.get(position);

                categoryColor = picked_color;

                List<String> colors = Arrays.asList(getResources().getStringArray(R.array.colors_array));
                int colorId = colors.indexOf(picked_color);

                View colorView = findViewById(R.id.LayoutColor);
                colorView = ItemBgColorManager.setBackgroundByColorName(colorView, colorId);
                colorView.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
