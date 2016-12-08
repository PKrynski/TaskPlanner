package com.taskplanner.pk.taskplanner;

import java.util.ArrayList;

public class TasksDB {

    private static ArrayList<Task> myTasks = new ArrayList<>();

    public static void addNewTask(Task task) {
        myTasks.add(task);
    }

    public static ArrayList<Task> getMyTasks() {
        return myTasks;
    }

    public static void setMyTasks(ArrayList<Task> modifiedTasks) {
        myTasks = modifiedTasks;
    }
}
