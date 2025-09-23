package com.tracker.manager;

import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.tracker.collection.CustomList;
import com.tracker.collection.Task;
import com.tracker.patterns.EvenOddSortStrategy;
import com.tracker.patterns.TaskComparators;
import com.tracker.patterns.strategy.BubbleSortStrategy;
import com.tracker.patterns.strategy.InsertionSortStrategy;
import com.tracker.patterns.strategy.SelectionSortStrategy;
import com.tracker.patterns.strategy.SortStrategy;

public class SortManager {
    private final SortStrategy<Task> bubbleSort;
    private final SortStrategy<Task> insertionSort;
    private final SortStrategy<Task> selectionSort;
    private final ExecutorService executor;

    public SortManager() {
        this.bubbleSort = new BubbleSortStrategy<>();
        this.insertionSort = new InsertionSortStrategy<>();
        this.selectionSort = new SelectionSortStrategy<>();
        this.executor = Executors.newFixedThreadPool(2);
    }

    public CustomList<Task> sortTasks(CustomList<Task> allTasks, String sortType) {
        if (allTasks == null || allTasks.isEmpty()) {
            return new CustomList<>();
        }

        CustomList<Task> tasksToSort = new CustomList<>(allTasks);
        Comparator<Task> comparator = getComparator(sortType);
        SortStrategy<Task> strategy = getSortStrategy(sortType);

        // Для EvenOdd сохраняем однопоточное поведение (позиции нечетных фиксированы)
        if (strategy instanceof EvenOddSortStrategy) {
            strategy.sort(tasksToSort, comparator);
            return tasksToSort;
        }

        // Параллельная сортировка (2 потока) и собственное слияние
        int mid = tasksToSort.size() / 2;
        CustomList<Task> left = tasksToSort.subList(0, mid);
        CustomList<Task> right = tasksToSort.subList(mid, tasksToSort.size());

        Future<?> leftFuture = executor.submit(() -> {
            if (strategy != null) {
                strategy.sort(left, comparator);
            } else {
                // Без fallback на стандартные реализации — используем пузырьковую как базовую
                bubbleSort.sort(left, comparator);
            }
        });

        Future<?> rightFuture = executor.submit(() -> {
            if (strategy != null) {
                strategy.sort(right, comparator);
            } else {
                bubbleSort.sort(right, comparator);
            }
        });

        try {
            leftFuture.get();
            rightFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Ошибка параллельной сортировки", e);
        }

        return merge(left, right, comparator);
    }

    private Comparator<Task> getComparator(String sortType) {
        return switch (sortType) {
            case "1" -> TaskComparators.byPriority();
            case "2" -> Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()));
            case "3" -> TaskComparators.byTitle();
            default -> TaskComparators.byId();
        };
    }

    private SortStrategy<Task> getSortStrategy(String sortType) {
        return switch (sortType) {
            case "4" -> selectionSort; // вместо стандартной сортировки используем SelectionSort
            case "5" -> bubbleSort;
            case "6" -> insertionSort; // MergeSort заменен на InsertionSort
            case "7" -> new EvenOddSortStrategy<>(bubbleSort, Task::getId);
            default -> null;
        };
    }

    public void shutdown() {
        executor.shutdown();
        System.out.println("SortManager завершен");
    }

    private CustomList<Task> merge(CustomList<Task> left, CustomList<Task> right, Comparator<Task> comparator) {
        CustomList<Task> result = new CustomList<>(left.size() + right.size());
        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        while (i < left.size()) {
            result.add(left.get(i++));
        }
        while (j < right.size()) {
            result.add(right.get(j++));
        }
        return result;
    }
}
