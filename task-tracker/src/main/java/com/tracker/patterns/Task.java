package main.java.com.tracker.patterns;

import java.util.Objects;

public class Task {
    private final int id;
    private final String title;
    private final int priority;
    private final TaskStatus status;

    private Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.priority = builder.priority;
        this.status = builder.status;
    }

    // Геттеры
    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getPriority() { return priority; }
    public TaskStatus getStatus() { return status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && priority == task.priority &&
                Objects.equals(title, task.title) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, priority, status);
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "', priority=" + priority +
                ", status=" + status + '}';
    }

    // Builder класс
    public static class Builder {
        private int id;
        private String title;
        private int priority;
        private TaskStatus status = TaskStatus.NEW;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder status(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Task build() {
            if (title == null) {
                throw new IllegalStateException("Title cannot be null");
            }
            if (priority < 0) {
                throw new IllegalStateException("Priority cannot be negative");
            }
            return new Task(this);
        }
    }
}
