package com.tracker.collection;

import java.util.*;
import java.util.stream.Stream;

public class CustomList<T> implements Iterable<T> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("rawtypes")
    private static final CustomList EMPTY = new CustomList<>(0);

    public CustomList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public CustomList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Емкость не может быть отрицательной");
        }
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

    public CustomList(CustomList<? extends T> other) {
        Objects.requireNonNull(other, "Исходный список не может быть null");
        this.elements = Arrays.copyOf(other.elements, other.size);
        this.size = other.size;
    }

    public void add(T element) {
        ensureCapacity(size+1);
        elements[size++] = element;
    }

    public void addAll(CustomList<? extends T> other) {
        if (other.size == 0) return;
        ensureCapacity(size + other.size);
        System.arraycopy(other.elements, 0, elements, size, other.size);
        size += other.size;
    }


    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", размер: " + size);
        }
        return (T) elements[index];
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", размер: " + size);
        }
        elements[index] = element;
    }


    @SuppressWarnings("unchecked")
    public static <T> CustomList<T> of() {
        return (CustomList<T>) EMPTY;
    }

    @SafeVarargs
    public static <T> CustomList<T> of(T... items) {
        Objects.requireNonNull(items, "Массив элементов не может быть null");
        CustomList<T> list = new CustomList<>(items.length);
        for (T item : items) {
            if (item == null) {
                throw new NullPointerException("Элемент списка не может быть null");
            }
            list.add(item);
        }
        return list;
    }

    public void sort(Comparator<? super T> comparator) {
        // Сортируем только заполненную часть массива
        Arrays.sort((T[]) elements, 0, size, comparator);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        return (T[]) Arrays.copyOf(elements, size);
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = Math.max(elements.length * 2, minCapacity);
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    public CustomList<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException(
                    "Диапазон: fromIndex=" + fromIndex + ", toIndex=" + toIndex + ", size=" + size
            );
        }
        CustomList<T> sub = new CustomList<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            @SuppressWarnings("unchecked")
            T element = (T) elements[i];
            sub.add(element);
        }
        return sub;
    }

    private class CustomIterator implements Iterator<T> {
        private int currentIndex = 0;
        private int lastReturnedIndex = -1;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) elements[currentIndex++];
        }

        public void set(T element) {
            if (lastReturnedIndex < 0) {
                throw new IllegalStateException(
                        "set() можно вызвать только после next() и один раз до следующего next()"
                );
            }
            elements[lastReturnedIndex] = element;
        }
    }

    public Stream<T> stream() {
        return Arrays.stream(toArray());
    }
}