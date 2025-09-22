package com.tracker.integration;

import java.util.List;
import java.util.Scanner;

import com.tracker.collection.CustomList;
import com.tracker.collection.Task;

/**
 * Интерфейс для работы с файлами от модуля файлов
 * Позволяет интегрироваться с функционалом чтения/записи файлов
 */
public interface FileDataProvider {
    /**
     * Загрузить задачи из файла
     * @param filePath путь к файлу
     * @return список загруженных задач
     */
    CustomList<Task> loadFromFile(String filePath);
    
    /**
     * Сохранить задачи в файл (append режим)
     * @param filePath путь к файлу
     * @param tasks список задач для сохранения
     * @return true если сохранение успешно
     */
    boolean saveToFile(String filePath, CustomList<Task> tasks);
    
    /**
     * Сгенерировать случайные задачи
     * @param count количество задач
     * @return список сгенерированных задач
     */
    CustomList<Task> generateRandomTasks(int count);

    CustomList<Task> generateManualTasks(int count, Scanner sc);


}
