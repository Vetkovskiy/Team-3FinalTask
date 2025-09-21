package com.tracker.manager;

import java.util.ArrayList;
import java.util.List;

import com.tracker.collection.Task;

public class TaskManager {
    private final List<Task> tasks;
    private int nextId;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public void addTasks(List<Task> tasks) {
        for (Task task : tasks) {
            addTask(task);
        }
    }

    public void updateTasks(List<Task> sortedTasks) {
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
