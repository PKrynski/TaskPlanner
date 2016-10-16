package com.taskplanner.pk.taskplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class NewCategoryActivity extends AppCompatActivity {

    private String categoryColor;
    private List<String> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        makeColorSpinner();
        colors = Arrays.asList(getResources().getStringArray(R.array.colors_array));
    }

    public void createNewCategory(View view) {

        EditText editTextName = (EditText) findViewById(R.id.editTextCategoryName);
        String categoryName = editTextName.getText().toString();

        if("".equals(categoryName)) {
            Toast.makeText(this, "You have to enter a category name.", Toast.LENGTH_SHORT).show();
        } else {

            CategoriesManager.addNewCategory(categoryName, categoryColor);

            String message = "New category '" + categoryName + "' created!";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public void makeColorSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinnerColorSelect);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoryColor = colors.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
