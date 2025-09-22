package com.tracker.patterns.strategy;

import com.tracker.collection.CustomList;

import java.util.Comparator;
import java.util.List;

public interface SortStrategy<T> {
    void sort(CustomList<T> list, Comparator<T> comparator);
}
