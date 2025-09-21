package com.tracker.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.tracker.collection.Task;
import com.tracker.patterns.BinarySearchUtil;
import com.tracker.patterns.TaskComparators;

public class SearchManager {
    private final ExecutorService executorService;

    public SearchManager() {
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public List<Task> searchByTitle(List<Task> allTasks, String title) {
        if (allTasks == null || title == null) {
            return new ArrayList<>();
        }
        
        return allTasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Task> searchByPriority(List<Task> allTasks, Task.Priority priority) {
        if (allTasks == null || priority == null) {
            return new ArrayList<>();
        }
        
        return allTasks.stream()
                .filter(task -> task.getPriority() == priority.ordinal() + 1)
                .collect(Collectors.toList());
    }

    public Task binarySearchById(List<Task> allTasks, int id) {
        if (allTasks == null || allTasks.isEmpty()) {
            return null;
        }
        
        // Создаем копию и сортируем по ID для бинарного поиска
        List<Task> sortedTasks = new ArrayList<>(allTasks);
        sortedTasks.sort(TaskComparators.byId());
        
        // Создаем задачу для поиска
        Task searchTask = new Task(id, "", "", Task.Priority.LOW, Task.Status.NEW, null);
        
        int index = BinarySearchUtil.binarySearch(sortedTasks, searchTask, TaskComparators.byId());
        return index >= 0 ? sortedTasks.get(index) : null;
    }

    public List<Task> searchUsingStreams(List<Task> allTasks, String term) {
        if (allTasks == null || term == null) {
            return new ArrayList<>();
        }
        
        return allTasks.stream()
                .filter(task -> 
                    task.getTitle().toLowerCase().contains(term.toLowerCase()) ||
                    (task.getDescription() != null && task.getDescription().toLowerCase().contains(term.toLowerCase()))
                )
                .collect(Collectors.toList());
    }

    public Map<String, Long> countOccurrencesMultiThreaded(List<Task> allTasks, String term) {
        if (allTasks == null || term == null) {
            return new ConcurrentHashMap<>();
        }
        
        Map<String, Long> result = new ConcurrentHashMap<>();
        
        try {
            // Разделяем задачи на части для многопоточного поиска
            int chunkSize = Math.max(1, allTasks.size() / 4);
            List<Future<Map<String, Long>>> futures = new ArrayList<>();
            
            for (int i = 0; i < allTasks.size(); i += chunkSize) {
                int endIndex = Math.min(i + chunkSize, allTasks.size());
                List<Task> chunk = allTasks.subList(i, endIndex);
                
                Future<Map<String, Long>> future = executorService.submit(() -> 
                    countOccurrencesInChunk(chunk, term)
                );
                futures.add(future);
            }
            
            // Собираем результаты
            for (Future<Map<String, Long>> future : futures) {
                Map<String, Long> chunkResult = future.get();
                chunkResult.forEach((key, value) -> 
                    result.merge(key, value, Long::sum)
                );
            }
            
        } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
            System.err.println("Ошибка многопоточного поиска: " + e.getMessage());
            // Fallback на обычный поиск
            return countOccurrencesInChunk(allTasks, term);
        }
        
        return result;
    }
    
    private Map<String, Long> countOccurrencesInChunk(List<Task> tasks, String term) {
        Map<String, Long> counts = new ConcurrentHashMap<>();
        String lowerTerm = term.toLowerCase();
        
        for (Task task : tasks) {
            // Подсчет в названии
            String title = task.getTitle().toLowerCase();
            long titleCount = title.split(lowerTerm, -1).length - 1;
            if (titleCount > 0) {
                counts.merge("title", titleCount, Long::sum);
            }
            
            // Подсчет в описании
            if (task.getDescription() != null) {
                String description = task.getDescription().toLowerCase();
                long descCount = description.split(lowerTerm, -1).length - 1;
                if (descCount > 0) {
                    counts.merge("description", descCount, Long::sum);
                }
            }
        }
        
        return counts;
    }

    public void shutdown() {
        executorService.shutdown();
        System.out.println("SearchManager завершен");
    }
}
