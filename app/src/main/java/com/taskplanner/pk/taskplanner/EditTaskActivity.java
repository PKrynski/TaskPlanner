package com.taskplanner.pk.taskplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class EditTaskActivity extends AppCompatActivity {

    private String taskCategory;
    ArrayList<Task> myTasks = TasksDB.getMyTasks();
    Task currentTask;
    int taskID;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.yes_no_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_accept) {
            updateTask();
        } else if (id == R.id.action_decline) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        taskID = intent.getIntExtra("taskID", 0);
        fillTaskDetails();
        makeCategorySpinner();
    }

    private void fillTaskDetails() {

        currentTask = myTasks.get(taskID);

        EditText taskName = (EditText) findViewById(R.id.editTextTaskName);
        EditText taskDescription = (EditText) findViewById(R.id.editTextTaskDescription);

        taskName.setText(currentTask.getName());
        taskDescription.setText(currentTask.getDescription());
    }


    public void updateTask() {

        EditText editTextName = (EditText) findViewById(R.id.editTextTaskName);
        String taskName = editTextName.getText().toString();

        EditText editTextDescription = (EditText) findViewById(R.id.editTextTaskDescription);
        String taskDescription = editTextDescription.getText().toString();

        if ("".equals(taskName)) {
            Toast.makeText(this, "You have to enter a task name.", Toast.LENGTH_SHORT).show();
        } else {

            currentTask.update(taskName, taskCategory, taskDescription);

            saveTasksInSharedPreferences();
            finish();
        }

    }

    public void makeCategorySpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategorySelect);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CategoriesManager.getCategoriesNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int currentCategoryID = CategoriesManager.getCategoriesNames().indexOf(currentTask.getCategory());
        spinner.setSelection(currentCategoryID);

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

    private void saveTasksInSharedPreferences() {

        ArrayList<Task> myTasks = TasksDB.getMyTasks();

        SharedPreferences prefs = getSharedPreferences("TasksPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String tasks = gson.toJson(myTasks);

        editor.putString("tasks", tasks);
        editor.apply();
    }
}
