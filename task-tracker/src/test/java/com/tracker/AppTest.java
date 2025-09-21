package test.java.com.tracker;

import main.java.com.tracker.patterns.BinarySearchUtil;
import main.java.com.tracker.patterns.Task;
import main.java.com.tracker.patterns.TaskComparators;
import main.java.com.tracker.patterns.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    void testAppRuns() {
        assertTrue(true, "Базовый тест работает!");
    }

    @Test
    void testTaskBuilder() {
        Task task = new Task.Builder()
                .id(1)
                .title("Test Task")
                .priority(5)
                .status(TaskStatus.NEW)
                .build();

        assertEquals(1, task.getId());
        assertEquals("Test Task", task.getTitle());
        assertEquals(5, task.getPriority());
        assertEquals(TaskStatus.NEW, task.getStatus());
    }

    @Test
    void testBinarySearch() {
        List<Task> tasks = createTestTasks();
        tasks.sort(TaskComparators.byId());

        Task searchTask = tasks.get(1);
        int index = BinarySearchUtil.binarySearch(tasks, searchTask, TaskComparators.byId());

        assertEquals(1, index);
    }

    private List<Task> createTestTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task.Builder().id(3).title("C").priority(3).build());
        tasks.add(new Task.Builder().id(1).title("A").priority(1).build());
        tasks.add(new Task.Builder().id(2).title("B").priority(2).build());
        return tasks;
    }
}
