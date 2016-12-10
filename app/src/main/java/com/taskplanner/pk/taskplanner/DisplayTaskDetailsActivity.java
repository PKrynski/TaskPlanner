package com.taskplanner.pk.taskplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DisplayTaskDetailsActivity extends AppCompatActivity {

    ArrayList<Task> myTasks = TasksDB.getMyTasks();
    Task currentTask;
    int taskID;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if( id == R.id.action_edit) {
            Intent intent = new Intent(this, EditTaskActivity.class);
            intent.putExtra("taskID", taskID);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task_details);

        Intent intent = getIntent();
        taskID = intent.getIntExtra("taskID",0);
        displayDetails(taskID);
    }

    private void displayDetails(int taskID) {

        currentTask = myTasks.get(taskID);

        TextView taskName = (TextView) findViewById(R.id.displayTaskNameTextView);
        TextView taskCategory = (TextView) findViewById(R.id.displayTaskCategoryTextView);
        TextView taskDescription = (TextView) findViewById(R.id.displayTaskDetailsTextView);

        taskName.setText(currentTask.getName());
        taskCategory.setText(currentTask.getCategory());
        taskDescription.setText(currentTask.getDescription());

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox_details_iscomplete);
        checkBox.setChecked(currentTask.isCompleted());
    }

    public void changeTaskStatus(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        String message;

        if (checked) {
            currentTask.setAsComplete();
            message = getString(R.string.message_task_complete);
        } else {
            currentTask.setAsIncomplete();
            message = getString(R.string.message_task_incomplete);
        }

        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        updateTasksInSharedPreferences();
    }

    private void updateTasksInSharedPreferences() {

        ArrayList<Task> myTasks = TasksDB.getMyTasks();

        SharedPreferences prefs = getSharedPreferences("TasksPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String tasks = gson.toJson(myTasks);

        editor.putString("tasks", tasks);
        editor.apply();
    }
}
