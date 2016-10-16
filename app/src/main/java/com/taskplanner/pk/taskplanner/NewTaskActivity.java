package com.taskplanner.pk.taskplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NewTaskActivity extends AppCompatActivity {

    private String taskCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        makeCategorySpinner();
    }

    public void createNewTask(View view) {

        EditText editTextName = (EditText) findViewById(R.id.editTextCategoryName);
        String taskName = editTextName.getText().toString();

        EditText editTextDescription = (EditText) findViewById(R.id.editTextTaskDescription);
        String taskDescription = editTextDescription.getText().toString();

        if("".equals(taskName)) {
            Toast.makeText(this, "You have to enter a task name.", Toast.LENGTH_SHORT).show();
        } else {

            Task newTask = new Task(taskName, taskDescription, taskCategory);
            TasksDB.addNewTask(newTask);

            String message = "New task '" + taskName + "' created!";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public void makeCategorySpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategorySelect);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayList<String> categories = CategoriesManager.getCategoriesNames();

                String selected_category = categories.get(position);
                taskCategory = selected_category;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
