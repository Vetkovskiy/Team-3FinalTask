package com.tracker.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.tracker.collection.Task;
import com.tracker.patterns.TaskComparators;
import com.tracker.patterns.strategy.BubbleSortStrategy;
import com.tracker.patterns.strategy.InsertionSortStrategy;
import com.tracker.patterns.strategy.SortStrategy;

public class SortManager {
    private final SortStrategy<Task> bubbleSort;
    private final SortStrategy<Task> insertionSort;

    public SortManager() {
        this.bubbleSort = new BubbleSortStrategy<>();
        this.insertionSort = new InsertionSortStrategy<>();
    }

    public List<Task> sortTasks(List<Task> allTasks, String sortType) {
        if (allTasks == null || allTasks.isEmpty()) {
            return new ArrayList<>();
        }

        List<Task> tasksToSort = new ArrayList<>(allTasks);
        Comparator<Task> comparator = getComparator(sortType);
        SortStrategy<Task> strategy = getSortStrategy(sortType);

        if (strategy != null) {
            strategy.sort(tasksToSort, comparator);
        } else {
            // Fallback на стандартную сортировку
            tasksToSort.sort(comparator);
        }

        return tasksToSort;
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
            case "4" -> null; // QuickSort - используем стандартную сортировку
            case "5" -> bubbleSort;
            case "6" -> insertionSort; // MergeSort заменен на InsertionSort
            default -> null;
        };
    }

    public void shutdown() {
        // Очистка ресурсов если необходимо
        System.out.println("SortManager завершен");
    }
}
