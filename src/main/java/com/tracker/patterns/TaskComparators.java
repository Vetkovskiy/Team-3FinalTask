package com.tracker.patterns;

import java.util.Comparator;

import com.tracker.collection.Task;

public class TaskComparators {

    public static Comparator<Task> byId() {
        return Comparator.comparingInt(Task::getId);
    }

    public static Comparator<Task> byTitle() {
        return Comparator.comparing(Task::getTitle);
    }

    public static Comparator<Task> byPriority() {
        return Comparator.comparing(Task::getPriority);
    }

    public static Comparator<Task> byStatus() {
        return Comparator.comparing(Task::getStatus);
    }
}
