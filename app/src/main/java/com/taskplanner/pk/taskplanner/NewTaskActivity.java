package com.taskplanner.pk.taskplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

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
            Toast.makeText(this, getString(R.string.task_name_required), Toast.LENGTH_SHORT).show();
        } else {

            Task newTask = new Task(taskName, taskDescription, taskCategory);
            TasksDB.addNewTask(newTask);

            saveTasksInSharedPreferences();
            finish();
        }

    }

    public void makeCategorySpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategorySelect);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CategoriesManager.getCategoriesNames());
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
