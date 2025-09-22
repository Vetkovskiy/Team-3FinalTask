package com.tracker.collection;

import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

import com.tracker.collection.Task.Status;


public class TaskGenerator {
    public static CustomList<Task> generateRandom(int count) {
        Random rnd = new Random();
        CustomList<Task> list = new CustomList<>();
        IntStream.range(0, count).forEach(id -> {
            String title = "Task_" + (100 + rnd.nextInt(900));
            int priority = 1 + rnd.nextInt(10);
            Status status = Status.values()[rnd.nextInt(Status.values().length)];
            list.add(new Task(id, title, priority, status));
        });
        return list;
    }

    public static CustomList<Task> generateManual(int count, Scanner sc) {
        CustomList<Task> list = new CustomList<>();
        IntStream.range(0, count).forEach(i -> {
            System.out.println("Введите задачу #" + (i + 1));

            int id = readId(sc);
            System.out.print("Название: ");
            String title = sc.nextLine();

            int priority = readPriority(sc);
            Task.Status status = readStatus(sc);

            list.add(new Task(id, title, priority, status));
        });

        return list;
    }

    private static int readId(Scanner sc) {
        while (true) {
            try {
                System.out.print("ID: ");
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Неверный ввод, попробуйте снова.");
            }
        }
    }

    private static Status readStatus(Scanner sc) {
        while (true) {
            System.out.print("Статус (NEW, PROCESSING, FAILED, SUCCESS): ");
            try {
                return Status.valueOf(sc.nextLine().trim().toUpperCase());
            } catch (Exception e) {
                System.out.println("Неверный статус.");
            }
        }
    }

    private static int readPriority(Scanner sc) {
        while (true) {
            try {
                System.out.print("Приоритет (1–10): ");
                int priority = Integer.parseInt(sc.nextLine().trim());
                if (priority >= 1 && priority <= 10) {
                    return priority;
                } else {
                    System.out.println("Приоритет должен быть от 1 до 10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод, попробуйте снова.");
            }
        }
    }

}
