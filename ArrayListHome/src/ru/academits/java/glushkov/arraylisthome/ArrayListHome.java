package ru.academits.java.glushkov.arraylisthome;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayListHome {
    public static void main(String[] args) {
        System.out.println("1)");
        ArrayList<String> list1 = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream("input.txt"))) {
            while (scanner.hasNext()) {
                list1.add(scanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла");
        }

        System.out.println("list1 = " + list1);

        System.out.println();
        System.out.println("2)");

        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(1, 2, 2, 3, 4, 5, 6, 7, 8, 8, 9));
        System.out.println("Исходный список:");
        System.out.println("list2 = " + list2);

        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i) % 2 == 0) {
                list2.remove(i);
                i--;
            }
        }

        System.out.println();
        System.out.println("Список после удаления четных чисел:");
        System.out.println("list2 = " + list2);

        System.out.println();
        System.out.println("3)");

        ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList(1, 5, 2, 1, 3, 5));
        ArrayList<Integer> list4 = new ArrayList<>();

        System.out.println("Список с повторениями list3 = " + list3);

        for (Integer item : list3) {
            if (!list4.contains(item)) {
                list4.add(item);
            }
        }

        System.out.println("Список без повторений list4 = " + list4);
    }
}