package com.tracker.patterns;

import com.tracker.collection.CustomList;

import java.util.Comparator;
import java.util.List;

public class BinarySearchUtil {

    public static <T> int binarySearch(CustomList<T> list, T key, Comparator<T> comparator) {
        return binarySearch(list, key, comparator, 0, list.size() - 1);
    }

    private static <T> int binarySearch(CustomList<T> list, T key, Comparator<T> comparator,
                                        int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;
        int comparison = comparator.compare(list.get(mid), key);

        if (comparison == 0) {
            return mid;
        } else if (comparison < 0) {
            return binarySearch(list, key, comparator, mid + 1, high);
        } else {
            return binarySearch(list, key, comparator, low, mid - 1);
        }
    }
}
