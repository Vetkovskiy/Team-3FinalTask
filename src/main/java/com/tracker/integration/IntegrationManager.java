package com.tracker.integration;

import com.tracker.collection.CustomList;
import com.tracker.collection.Task;

import java.util.List;

/**
 * Менеджер интеграции - центральный компонент для работы с модулями команды
 * Позволяет подключать и использовать функционал от других участников
 */
public class IntegrationManager {
    private TaskProvider taskProvider;
    private FileDataProvider fileDataProvider;
    private SortProvider sortProvider;
    private SearchProvider searchProvider;
    
    // Конструктор
    public IntegrationManager() {
        // Инициализация заглушками - будут заменены реальными реализациями
        this.taskProvider = new MockTaskProvider();
        this.fileDataProvider = new MockFileDataProvider();
        this.sortProvider = new MockSortProvider();
        this.searchProvider = new MockSearchProvider();
    }
    
    // Геттеры для доступа к провайдерам
    public TaskProvider getTaskProvider() { return taskProvider; }
    public FileDataProvider getFileDataProvider() { return fileDataProvider; }
    public SortProvider getSortProvider() { return sortProvider; }
    public SearchProvider getSearchProvider() { return searchProvider; }
    
    // Методы для замены провайдеров на реальные реализации
    public void setTaskProvider(TaskProvider taskProvider) {
        this.taskProvider = taskProvider;
    }
    
    public void setFileDataProvider(FileDataProvider fileDataProvider) {
        this.fileDataProvider = fileDataProvider;
    }
    
    public void setSortProvider(SortProvider sortProvider) {
        this.sortProvider = sortProvider;
    }
    
    public void setSearchProvider(SearchProvider searchProvider) {
        this.searchProvider = searchProvider;
    }
    
    // Заглушки для тестирования
    private static class MockTaskProvider implements TaskProvider {
        @Override
        public CustomList<Object> getAllTasks() {
            System.out.println("📝 MockTaskProvider: получение задач (заглушка)");
            return CustomList.of();
        }
        
        @Override
        public int getTaskCount() {
            return 0;
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
    }
    
    private static class MockFileDataProvider implements FileDataProvider {
        @Override
        public CustomList<Task> loadFromFile(String filePath) {
            System.out.println("📁 MockFileDataProvider: загрузка из файла " + filePath + " (заглушка)");
            return CustomList.of();
        }
        
        @Override
        public boolean saveToFile(String filePath, CustomList<Task> tasks) {
            System.out.println("💾 MockFileDataProvider: сохранение в файл " + filePath + " (заглушка)");
            return true;
        }
        
        @Override
        public CustomList<Task> generateRandomTasks(int count) {
            System.out.println("🎲 MockFileDataProvider: генерация " + count + " случайных задач (заглушка)");
            return CustomList.of();
        }
    }
    
    private static class MockSortProvider implements SortProvider {
        @Override
        public CustomList<Object> sortById(CustomList<Object> tasks) {
            System.out.println("🔄 MockSortProvider: сортировка по ID (заглушка)");
            return tasks;
        }
        
        @Override
        public CustomList<Object> sortByTitle(CustomList<Object> tasks) {
            System.out.println("🔄 MockSortProvider: сортировка по названию (заглушка)");
            return tasks;
        }
        
        @Override
        public CustomList<Object> sortByPriority(CustomList<Object> tasks) {
            System.out.println("🔄 MockSortProvider: сортировка по приоритету (заглушка)");
            return tasks;
        }
        
        @Override
        public CustomList<Object> sortEvenOnly(CustomList<Object> tasks) {
            System.out.println("🔄 MockSortProvider: сортировка чётных элементов (заглушка)");
            return tasks;
        }
    }
    
    private static class MockSearchProvider implements SearchProvider {
        @Override
        public Object binarySearchById(CustomList<Object> tasks, int id) {
            System.out.println("🔍 MockSearchProvider: бинарный поиск по ID " + id + " (заглушка)");
            return null;
        }
        
        @Override
        public CustomList<Object> searchByTitle(CustomList<Object> tasks, String title) {
            System.out.println("🔍 MockSearchProvider: поиск по названию '" + title + "' (заглушка)");
            return CustomList.of();
        }
        
        @Override
        public CustomList<Object> searchByPriority(CustomList<Object> tasks, int priority) {
            System.out.println("🔍 MockSearchProvider: поиск по приоритету " + priority + " (заглушка)");
            return CustomList.of();
        }
    }
}
