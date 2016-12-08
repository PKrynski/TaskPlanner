package com.taskplanner.pk.taskplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Task> myTasks = TasksDB.getMyTasks();
    ArrayList<Task> allCurrentTasks;
    ArrayAdapter<Task> adapter;

    @Override
    protected void onResume() {
        super.onResume();

        loadAllCategories();
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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupDefaultCategories();
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
            runListAllCategoriesActivity();
        } else if (id == R.id.nav_completed) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupDefaultCategories() {

        CategoriesManager.addNewCategory(getString(R.string.uncategorized), "default");
        CategoriesManager.addNewCategory(getString(R.string.home), "orange");
        CategoriesManager.addNewCategory(getString(R.string.work), "blue");
        CategoriesManager.addNewCategory(getString(R.string.sport), "green");
        CategoriesManager.addNewCategory(getString(R.string.travel), "purple");
    }

    public void loadAllTasks() {

        allCurrentTasks = getTasksFromSharedPreferences();

        adapter = new myTasksListAdapter();

        ListView list = (ListView) findViewById(R.id.tasksListView);
        list.setAdapter(adapter);
    }

    private ArrayList<Task> getTasksFromSharedPreferences() {

        SharedPreferences prefs = getSharedPreferences("TasksPrefs", MODE_PRIVATE);
        String categories = prefs.getString("tasks", "None");

        if (!"None".equals(categories)) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Task>>(){}.getType();
            ArrayList<Task> readTasks = gson.fromJson(categories, type);
            TasksDB.setMyTasks(readTasks);

            return readTasks;
        }
        return myTasks;
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

    private void loadAllCategories() {

        SharedPreferences prefs = getSharedPreferences("CategoriesPrefs", MODE_PRIVATE);
        String categories = prefs.getString("categories", "None");

        if (!"None".equals(categories)) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Category>>(){}.getType();
            ArrayList<Category> readCategories = gson.fromJson(categories, type);
            CategoriesManager.setMyCategories(readCategories);
        }
    }

    public void runNewTaskActivity() {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    public void displayTaskDetails(int id) {
        Intent intent = new Intent(this, DisplayTaskDetailsActivity.class);
        intent.putExtra("taskID", id);
        startActivity(intent);
    }

    public void runListAllCategoriesActivity() {
        Intent intent = new Intent(this, ListAllCategoriesActivity.class);
        startActivity(intent);
    }

    private void registerClicks() {

        final ListView list = (ListView) findViewById(R.id.tasksListView);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View viewClicked, final int position, long id) {

                final Task currentTask = allCurrentTasks.get(position);

                boolean complete = currentTask.isCompleted();

                if (complete) {

                    Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.move_right);

                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            allCurrentTasks.remove(currentTask);
                            adapter.notifyDataSetChanged();
                            updateTasksInSharedPreferences();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    list.getChildAt(position).startAnimation(anim);

                } else {
                    displayTaskDetails(position);
                }
            }
        });

    }

    private class myTasksListAdapter extends ArrayAdapter<Task> {

        public myTasksListAdapter() {
            super(MainActivity.this, R.layout.task_item_view, allCurrentTasks);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.task_item_view, parent, false);
            }

            Task currentTask = allCurrentTasks.get(position);

            TextView nameTextView = (TextView) itemView.findViewById(R.id.item_textView_task_name);
            nameTextView.setText(currentTask.getName());

            TextView categoryTextView = (TextView) itemView.findViewById(R.id.item_textView_task_category);
            categoryTextView.setText(currentTask.getCategory());

            String taskCategory = currentTask.getCategory();

            CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox_completed);
            View sidebar = itemView.findViewById(R.id.right_sidebar);

            if (currentTask.isCompleted()) {
                checkBox.setChecked(true);
                sidebar.setBackgroundResource(R.color.colorGreen400);
            } else {
                checkBox.setChecked(false);
                sidebar.setBackgroundResource(0);
                itemView.jumpDrawablesToCurrentState();
            }

            itemView = ItemBgColorManager.setBackgroundByCategory(itemView, taskCategory);

            return itemView;
        }
    }

    public void markTaskAsCompleted(View view) {

        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();

        View sidebar = relativeLayout.findViewById(R.id.right_sidebar);

        ListView listView = (ListView) findViewById(R.id.tasksListView);

        int index = listView.indexOfChild(relativeLayout);

        Task currentTask = allCurrentTasks.get(index);

        boolean checked = ((CheckBox) view).isChecked();

        String message;

        if (checked) {
            activateSidebar(sidebar);

            currentTask.setAsComplete();
            message = "Task marked as complete.\nTap task to remove it from the list.";
        } else {
            deactivateSidebar(sidebar);

            currentTask.setAsIncomplete();
            message = "Task marked as incomplete.";
        }

        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    private void activateSidebar(View sidebar) {

        sidebar.setBackgroundResource(R.color.colorGreen400);
        Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_left);
        sidebar.startAnimation(anim);
    }

    private void deactivateSidebar(final View sidebar) {

        Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_right);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sidebar.setBackgroundResource(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        sidebar.startAnimation(anim);

    }

}
