package com.tracker.manager;

import com.tracker.collection.CustomList;
import com.tracker.collection.Task;

public class TaskManager {
    private final CustomList<Task> tasks;
    private int nextId;

    public TaskManager() {
        this.tasks = new CustomList<>();
        this.nextId = 1;
    }

    public CustomList<Task> getAllTasks() {
        return new CustomList<>(tasks);
    }

    public void addTasks(CustomList<Task> tasks) {
        for (Task task : tasks) {
            addTask(task);
        }
    }

    public void updateTasks(CustomList<Task> sortedTasks) {
        tasks.clear();
        tasks.addAll(sortedTasks);
    }

    public int getNextId() {
        return nextId++;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
