package com.tracker.manager;

import java.util.List;
import java.util.Scanner;

import com.tracker.collection.Task;
import com.tracker.collection.TaskGenerator;
import com.tracker.integration.FileDataProvider;
import com.tracker.integration.FileDataProviderImpl;

public class DataSourceManager {
    private final FileDataProvider fileDataProvider;
    private final Scanner scanner;

    public DataSourceManager() {
        this.fileDataProvider = new FileDataProviderImpl();
        this.scanner = new Scanner(System.in);
    }

    public List<Task> loadFromFile(String filePath) {
        try {
            return fileDataProvider.loadFromFile(filePath);
        } catch (Exception e) {
            System.err.println("Ошибка загрузки из файла: " + e.getMessage());
            return List.of();
        }
    }

    public List<Task> generateRandomTasks(int count) {
        try {
            return fileDataProvider.generateRandomTasks(count);
        } catch (Exception e) {
            System.err.println("Ошибка генерации задач: " + e.getMessage());
            // Fallback на TaskGenerator
            return TaskGenerator.generateRandom(count).stream().toList();
        }
    }

    public List<Task> loadFromManualInput() {
        System.out.print("Сколько задач добавить? ");
        int count = Integer.parseInt(scanner.nextLine());
        return TaskGenerator.generateManual(count, scanner).stream().toList();
    }
}
