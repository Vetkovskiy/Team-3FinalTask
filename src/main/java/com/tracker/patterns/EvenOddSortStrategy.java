package com.tracker.patterns;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.tracker.patterns.strategy.SortStrategy;

public class EvenOddSortStrategy<T> implements SortStrategy<T> {
    private final SortStrategy<T> baseStrategy;

    public EvenOddSortStrategy(SortStrategy<T> baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        // Разделяем на четные и нечетные индексы
        List<T> evenElements = new ArrayList<>();
        List<T> oddElements = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                evenElements.add(list.get(i));
            } else {
                oddElements.add(list.get(i));
            }
        }

        // Сортируем только четные элементы
        baseStrategy.sort(evenElements, comparator);

        // Восстанавливаем список
        int evenIndex = 0;
        int oddIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                list.set(i, evenElements.get(evenIndex++));
            } else {
                list.set(i, oddElements.get(oddIndex++));
            }
        }
    }
}
