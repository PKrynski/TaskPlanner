package com.taskplanner.pk.taskplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayTaskDetailsActivity extends AppCompatActivity {

    ArrayList<Task> myTasks = TasksDB.getMyTasks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task_details);

        Intent intent = getIntent();
        int taskID = intent.getIntExtra("taskID",0);
        displayDetails(taskID);
    }

    private void displayDetails(int taskID) {

        Task currentTask = myTasks.get(taskID);

        TextView taskName = (TextView) findViewById(R.id.displayTaskNameTextView);
        TextView taskCategory = (TextView) findViewById(R.id.displayTaskCategoryTextView);
        TextView taskDescription = (TextView) findViewById(R.id.displayTaskDetailsTextView);

        taskName.setText(currentTask.getName());
        taskCategory.setText(currentTask.getCategory());
        taskDescription.setText(currentTask.getDescription());
    }
}
