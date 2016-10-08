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

        EditText editText = (EditText) findViewById(R.id.editText);
        String taskName = editText.getText().toString();

        if("".equals(taskName)) {
            Toast.makeText(this, "You have to enter a task name.", Toast.LENGTH_SHORT).show();
        } else {
            String toastmessage = "New task '" + taskName + "' created!";
            Toast.makeText(this, toastmessage, Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
