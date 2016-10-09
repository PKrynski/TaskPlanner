package com.taskplanner.pk.taskplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    public void createNewTaskList(View view) {

        EditText editTextName = (EditText) findViewById(R.id.editTextTaskName);
        String taskName = editTextName.getText().toString();

        EditText editTextDescription = (EditText) findViewById(R.id.editTextTaskDescription);
        String taskDescription = editTextDescription.getText().toString();

        if("".equals(taskName)) {
            Toast.makeText(this, "You have to enter a task name.", Toast.LENGTH_SHORT).show();
        } else {

            Task newTask = new Task(taskName, taskDescription, null);
            TasksDB.addNewTask(newTask);

            String toastmessage = "New task '" + taskName + "' created!";
            Toast.makeText(this, toastmessage, Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
