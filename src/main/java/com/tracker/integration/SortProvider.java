package com.tracker.integration;

import com.tracker.collection.CustomList;

/**
 * Интерфейс для сортировки от модуля паттернов (Дмитрий М)
 * Позволяет интегрироваться с паттерном Стратегия для сортировки
 */
public interface SortProvider {
    /**
     * Отсортировать задачи по ID
     * @param tasks список задач
     * @return отсортированный список
     */
    CustomList<Object> sortById(CustomList<Object> tasks);
    
    /**
     * Отсортировать задачи по названию
     * @param tasks список задач
     * @return отсортированный список
     */
    CustomList<Object> sortByTitle(CustomList<Object> tasks);
    
    /**
     * Отсортировать задачи по приоритету
     * @param tasks список задач
     * @return отсортированный список
     */
    CustomList<Object> sortByPriority(CustomList<Object> tasks);
    
    /**
     * Сортировка с условием "чётные сортируем, нечётные оставляем"
     * @param tasks список задач
     * @return отсортированный список
     */
    CustomList<Object> sortEvenOnly(CustomList<Object> tasks);
}
