package main.java.com.tracker.patterns;

import java.util.Comparator;

public class TaskComparators {

    public static Comparator<Task> byId() {
        return Comparator.comparingInt(Task::getId);
    }

    public static Comparator<Task> byTitle() {
        return Comparator.comparing(Task::getTitle);
    }

    public static Comparator<Task> byPriority() {
        return Comparator.comparingInt(Task::getPriority);
    }

    public static Comparator<Task> byStatus() {
        return Comparator.comparing(Task::getStatus);
    }
}
