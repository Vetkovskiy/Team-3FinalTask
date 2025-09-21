package com.tracker.collection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.tracker.integration.FileDataProviderImpl.PATTERN;

public class Task {
    private int id;
    private String title;
    private String description;
    private int priority;
    private Status status;
    private LocalDate dueDate;

    public Task(int id, String title, int priority, Status status) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.status = status;
    }

    public Task(int id, String title, String description, Priority priority, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority.ordinal() + 1;
        this.dueDate = dueDate;
    }

    public Task(int id, String title, String description, Priority priority, Status status, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority.ordinal() + 1;
        this.status = status;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        Priority[] values = Priority.values();
        Priority value = values[priority - 1];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);

        return String.join(",", String.valueOf(id), title, description, value.name(), status.name(), formatter.format(dueDate));
    }

    public enum Status {
        NEW,
        PROCESSING,
        FAILED,
        SUCCESS;

        public static String getValidValues() {
            return "PENDING, PROCESSING, FAILED, COMPLETED";
        }
    }

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT;

        public static String getValidValues() {
            return "LOW, MEDIUM, HIGH, URGENT";
        }
    }
}