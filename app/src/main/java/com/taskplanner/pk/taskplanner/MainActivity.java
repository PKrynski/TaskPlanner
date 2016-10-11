package com.taskplanner.pk.taskplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Task> myTasks = TasksDB.getMyTasks();

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "onResume called - tasks loaded",Toast.LENGTH_LONG).show();
        loadAllTasks();
        registerClicks();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runNewTaskActivity();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        CategoriesManager.setupDefaultCategories();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newtask) {
            runNewTaskActivity();
        } else if (id == R.id.nav_categories) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadAllTasks() {

        ArrayAdapter<Task> adapter = new myListAdapter();

        ListView list = (ListView) findViewById(R.id.tasksListView);
        list.setAdapter(adapter);
    }

    private void registerClicks() {

        Toast.makeText(MainActivity.this, "registerClicks running", Toast.LENGTH_LONG);

        ListView list = (ListView) findViewById(R.id.tasksListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                displayTaskDetails(position);
            }
        });

    }

    public void runNewTaskActivity() {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    public void displayTaskDetails(int id) {
        Intent intent = new Intent(this, DisplayTaskDetailsActivity.class);
        intent.putExtra("taskID",id);
        startActivity(intent);
    }

    private class myListAdapter extends ArrayAdapter<Task> {

        public myListAdapter() {
            super(MainActivity.this, R.layout.task_item_view, myTasks);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if(itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.task_item_view, parent, false);
            }

            Task currentTask = myTasks.get(position);

            TextView nameTextView = (TextView) itemView.findViewById(R.id.item_textView_task_name);
            nameTextView.setText(currentTask.getName());

            TextView categoryTextView = (TextView) itemView.findViewById(R.id.item_textView_task_category);
            categoryTextView.setText(currentTask.getCategory());

            String taskCategory = currentTask.getCategory();

            //TODO: Set background color based on task category
            itemView = ItemBgColorManager.setBackgroundByCategory(itemView,taskCategory);

            return itemView;
        }
    }
}
