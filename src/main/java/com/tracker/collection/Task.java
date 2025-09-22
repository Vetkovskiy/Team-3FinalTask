package com.tracker.collection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private final int id;
    private final String title;
    private String description;
    private final Priority priority;
    private final Status status;
    private LocalDate dueDate;

    private Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.status = builder.status;
        this.dueDate = builder.dueDate;
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

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public static class Builder {
        private int id;
        private String title;
        private String description;
        private Priority priority;
        private Status status;
        private LocalDate dueDate;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder dueDate (LocalDate dueDate){
            this.dueDate = dueDate;
            return this;
        }

        public Task build() {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalStateException("Название задачи не может быть пустым");
            }
            if (description == null || description.trim().isEmpty()) {
                throw new IllegalStateException("Описание задачи не может быть пустым");
            }
            if (priority== null) {
                throw new IllegalStateException("Приоритет должен быть от 1 до 10");
            }
            if (status == null) {
                throw new IllegalStateException("Статус задачи не может быть null");
            }

            if (dueDate == null){
                throw new IllegalStateException("Дата выполнения задачи не может быть null");
            }
            return new Task(this);
        }

    }
        @Override
    public String toString() {
        if (dueDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return String.join(",", String.valueOf(id), title, description, priority.name(), status.name(), formatter.format(dueDate));
        } else {
            return "Task{id=" + id + ", title='" + title + "', priority=" + priority + ", status=" + status + "}";
        }
    }

    public enum Status {
        NEW,
        PROCESSING,
        FAILED,
        SUCCESS;

        public static String getValidValues() {
            return "NEW, PROCESSING, FAILED, SUCCESS";
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