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
                .filter(task -> task.getTitle() != null && task.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Task> searchByPriority(List<Task> allTasks, Task.Priority priority) {
        if (allTasks == null || priority == null) {
            return new ArrayList<>();
        }
        int priorityValue = priority.ordinal() + 1;
        return allTasks.stream()
                .filter(task -> task.getPriority() == priorityValue)
                .collect(Collectors.toList());
    }

    public Task binarySearchById(List<Task> allTasks, int id) {
        if (allTasks == null || allTasks.isEmpty()) {
            return null;
        }
        List<Task> sorted = new ArrayList<>(allTasks);
        sorted.sort(TaskComparators.byId());
        Task probe = new Task(id, "", "", Task.Priority.LOW, Task.Status.NEW, null);
        int idx = BinarySearchUtil.binarySearch(sorted, probe, TaskComparators.byId());
        return idx >= 0 ? sorted.get(idx) : null;
    }

    public List<Task> searchUsingStreams(List<Task> allTasks, String term) {
        if (allTasks == null || term == null) {
            return new ArrayList<>();
        }
        String lower = term.toLowerCase();
        return allTasks.stream()
                .filter(task ->
                        (task.getTitle() != null && task.getTitle().toLowerCase().contains(lower)) ||
                        (task.getDescription() != null && task.getDescription().toLowerCase().contains(lower))
                )
                .collect(Collectors.toList());
    }

    public Map<String, Long> countOccurrencesMultiThreaded(List<Task> allTasks, String term) {
        if (allTasks == null || term == null) {
            return new ConcurrentHashMap<>();
        }
        Map<String, Long> result = new ConcurrentHashMap<>();
        try {
            int chunkSize = Math.max(1, allTasks.size() / 4);
            List<Future<Map<String, Long>>> futures = new ArrayList<>();
            for (int i = 0; i < allTasks.size(); i += chunkSize) {
                int end = Math.min(i + chunkSize, allTasks.size());
                List<Task> slice = allTasks.subList(i, end);
                futures.add(executorService.submit(() -> countOccurrencesInChunk(slice, term)));
            }
            for (Future<Map<String, Long>> future : futures) {
                Map<String, Long> part = future.get();
                part.forEach((k, v) -> result.merge(k, v, Long::sum));
            }
        } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
            System.err.println("Ошибка многопоточного подсчета: " + e.getMessage());
            return countOccurrencesInChunk(allTasks, term);
        }
        return result;
    }

    private Map<String, Long> countOccurrencesInChunk(List<Task> tasks, String term) {
        Map<String, Long> map = new ConcurrentHashMap<>();
        String lower = term.toLowerCase();
        for (Task t : tasks) {
            if (t.getTitle() != null) {
                long c = countOccurrences(t.getTitle().toLowerCase(), lower);
                if (c > 0) map.merge("title", c, Long::sum);
            }
            if (t.getDescription() != null) {
                long c = countOccurrences(t.getDescription().toLowerCase(), lower);
                if (c > 0) map.merge("description", c, Long::sum);
            }
        }
        return map;
    }

    private long countOccurrences(String text, String term) {
        if (term.isEmpty()) return 0;
        int idx = 0;
        long count = 0;
        while ((idx = text.indexOf(term, idx)) != -1) {
            count++;
            idx += term.length();
        }
        return count;
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
