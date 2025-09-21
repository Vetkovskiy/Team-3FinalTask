package com.tracker.integration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tracker.collection.Task;
import com.tracker.exception.FileProcessingException;

public class FileDataProviderImpl implements FileDataProvider {
    public static final String PATTERN = "dd-MM-yyyy";

    @Override
    public List<Task> loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<Task> tasks = new ArrayList<>();
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                Task task = parseTaskFromCSV(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            return tasks;
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            throw new FileProcessingException(e);
        }
    }

    private Task parseTaskFromCSV(String csvLine) {
        try {
            String[] values = csvLine.split(",");
            if (values.length != 6) {
                throw new FileProcessingException("В описании задачи должно быть ровно 6 колонок");
            }

            int id = parseId(values[0]);
            String title = parseTitle(values[1]);
            String description = parseDescription(values[2]);
            Task.Priority priority = parsePriority(values[3]);
            Task.Status status = parseStatus(values[4]);
            LocalDate dueDate = parseDueDate(values[5]);

            return new Task(id, title, description, priority, status, dueDate);

        } catch (Exception e) {
            System.err.println("Ошибка парсинга строки: " + csvLine + " - " + e.getMessage());
            return null;
        }
    }

    private int parseId(String idValue) {
        try {
            int id = Integer.parseInt(idValue);
            if (id <= 0) {
                throw new FileProcessingException("id должен быть положительным");
            }
            return id;
        } catch (Exception e) {
            throw new FileProcessingException("Ошибка парсинга id: " + idValue, e);
        }
    }

    private String parseTitle(String title) {
        if (title == null) {
            throw new FileProcessingException("Значение колонки title не может быть пустым");
        }
        if (title.length() > 50) {
            throw new FileProcessingException("Длина title не может быть больше 50 символов");
        }
        if (title.isBlank()) {
            throw new FileProcessingException("Значение колонки title не может быть пустым");
        }
        return title.trim();
    }

    private String parseDescription(String description) {
        if (description == null) {
            return null;
        }
        if (description.length() > 500) {
            throw new FileProcessingException("Длина description не может быть больше 500 символов");
        }
        return description.trim();
    }

    private Task.Priority parsePriority(String priority) throws FileProcessingException {
        if (priority == null) {
            throw new FileProcessingException("Приоритет задачи не может быть пустым");
        }
        try {
            return Task.Priority.valueOf(priority.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new FileProcessingException("Неверный приоритет: '" + priority +
                    "'. Допустимые значения: " + Task.Priority.getValidValues());
        }
    }

    private Task.Status parseStatus(String status) throws FileProcessingException {
        if (status == null) {
            throw new FileProcessingException("Статус задачи не может быть пустым");
        }
        try {
            return Task.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new FileProcessingException("Неверный Статус: '" + status +
                    "'. Допустимые значения: " + Task.Status.getValidValues());
        }
    }

    private LocalDate parseDueDate(String dueDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
            return LocalDate.parse(dueDate, formatter);
        } catch (DateTimeParseException e) {
            throw new FileProcessingException("Ошибка парсинга dueDate: " + dueDate, e);
        }
    }

    @Override
    public boolean saveToFile(String filePath, List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {

            for (Task task : tasks) {
                writer.append(task.toString());
                writer.newLine();
                System.out.println("Успешно добавлено " + tasks.size() + " задач");
            }
            writer.flush();
            return true;
        } catch (Exception e) {
            System.err.println("Не получилось сохранить в файл: " + tasks);
            return false;
        }
    }

    @Override
    public List<Task> generateRandomTasks(int count) {
        List<Task> tasks = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < count; i++) {
            int id = i + 1;
            String title = "Task_" + (100 + random.nextInt(900));
            String description = "Generated task " + (i + 1);
            Task.Priority priority = Task.Priority.values()[random.nextInt(Task.Priority.values().length)];
            Task.Status status = Task.Status.values()[random.nextInt(Task.Status.values().length)];
            LocalDate dueDate = LocalDate.now().plusDays(random.nextInt(30));
            
            tasks.add(new Task(id, title, description, priority, status, dueDate));
        }
        
        return tasks;
    }
}
