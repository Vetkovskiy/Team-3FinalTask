package com.tracker.patterns;

import java.util.Comparator;
import java.util.function.ToIntFunction;

import com.tracker.collection.CustomList;
import com.tracker.patterns.strategy.SortStrategy;

public class EvenOddSortStrategy<T> implements SortStrategy<T> {
    private final SortStrategy<T> baseStrategy;
    private final ToIntFunction<T> numericExtractor;

    public EvenOddSortStrategy(SortStrategy<T> baseStrategy, ToIntFunction<T> numericExtractor) {
        this.baseStrategy = baseStrategy;
        this.numericExtractor = numericExtractor;
    }

    @Override
    public void sort(CustomList<T> list, Comparator<T> comparator) {
        // Собираем элементы с четным значением числового поля
        CustomList<T> evenElements = new CustomList<>();
        CustomList<Integer> evenPositions = new CustomList<>();

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            int value = numericExtractor.applyAsInt(element);
            if (value % 2 == 0) {
                evenElements.add(element);
                evenPositions.add(i);
            }
        }

        // Сортируем только четные элементы выбранной базовой стратегией
        baseStrategy.sort(evenElements, comparator);

        // Возвращаем отсортированные четные элементы на исходные позиции,
        // нечетные остаются на местах
        int evenIndex = 0;
        for (int k = 0; k < evenPositions.size(); k++) {
            int pos = evenPositions.get(k);
            list.set(pos, evenElements.get(evenIndex++));
        }
    }
}
