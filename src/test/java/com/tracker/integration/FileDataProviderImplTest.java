package com.tracker.integration;

import com.tracker.collection.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileDataProviderImplTest {

    private FileDataProviderImpl provider = new FileDataProviderImpl();

    @Test
    @DisplayName("should Read 3 Valid Tasks")
    void test1() {
        //Given
        String filePath = "src/test/resources/Tasks1.csv";
        //When
        List<Task> tasks = provider.loadFromFile(filePath);
        //Then
        assertEquals(3, tasks.size());
    }

    @Test
    @DisplayName("Should read lines only with valid IDs")
    void test2() {
        //Given
        String filePath = "src/test/resources/Tasks2.csv";
        //When
        List<Task> tasks = provider.loadFromFile(filePath);
        //Then
        assertEquals(1, tasks.size());
    }

    @Test
    @DisplayName("Should read lines only with valid Titles")
    void test3() {
        //Given
        String filePath = "src/test/resources/Tasks3.csv";
        //When
        List<Task> tasks = provider.loadFromFile(filePath);
        //Then
        assertEquals(1, tasks.size());
    }

    @Test
    @DisplayName("Should read lines only with valid Description")
    void test4() {
        //Given
        String filePath = "src/test/resources/Tasks4.csv";
        //When
        List<Task> tasks = provider.loadFromFile(filePath);
        //Then
        assertEquals(3, tasks.size());
    }

    @Test
    @DisplayName("Should read lines only with valid Priority")
    void test5() {
        //Given
        String filePath = "src/test/resources/Tasks5.csv";
        //When
        List<Task> tasks = provider.loadFromFile(filePath);
        //Then
        assertEquals(2, tasks.size());
    }

    @Test
    @DisplayName("Should read lines only with valid Status")
    void test6() {
        //Given
        String filePath = "src/test/resources/Tasks6.csv";
        //When
        List<Task> tasks = provider.loadFromFile(filePath);
        //Then
        assertEquals(2, tasks.size());
    }

    @Test
    @DisplayName("Should read lines only with valid dueDate")
    void test7() {
        //Given
        String filePath = "src/test/resources/Tasks7.csv";
        //When
        List<Task> tasks = provider.loadFromFile(filePath);
        //Then
        assertEquals(1, tasks.size());
    }
}
