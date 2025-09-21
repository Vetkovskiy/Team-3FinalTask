package com.tracker.integration;

import com.tracker.collection.Task;

import java.util.List;

/**
 * Интерфейс для работы с файлами от модуля файлов (Дмитрий Г)
 * Позволяет интегрироваться с функционалом чтения/записи файлов
 */
public interface FileDataProvider {
    /**
     * Загрузить задачи из файла
     * @param filePath путь к файлу
     * @return список загруженных задач
     */
    List<Task> loadFromFile(String filePath);
    
    /**
     * Сохранить задачи в файл (append режим)
     * @param filePath путь к файлу
     * @param tasks список задач для сохранения
     * @return true если сохранение успешно
     */
    boolean saveToFile(String filePath, List<Task> tasks);

}
