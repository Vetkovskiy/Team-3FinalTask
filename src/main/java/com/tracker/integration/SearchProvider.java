package com.tracker.integration;

import com.tracker.collection.CustomList;

import java.util.List;

/**
 * Интерфейс для поиска от модуля паттернов (Дмитрий М)
 * Позволяет интегрироваться с бинарным поиском и другими методами поиска
 */
public interface SearchProvider {
    /**
     * Бинарный поиск по ID
     * @param tasks отсортированный список задач
     * @param id ID для поиска
     * @return найденная задача или null
     */
    Object binarySearchById(CustomList<Object> tasks, int id);
    
    /**
     * Поиск по названию
     * @param tasks список задач
     * @param title название для поиска
     * @return список найденных задач
     */
    CustomList<Object> searchByTitle(CustomList<Object> tasks, String title);
    
    /**
     * Поиск по приоритету
     * @param tasks список задач
     * @param priority приоритет для поиска
     * @return список найденных задач
     */
    CustomList<Object> searchByPriority(CustomList<Object> tasks, int priority);
}
