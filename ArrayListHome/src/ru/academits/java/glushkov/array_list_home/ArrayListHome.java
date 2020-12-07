package ru.academits.java.glushkov.array_list_home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayListHome {
    public static ArrayList<String> readFileLinesToArrayList(String fileName) {
        ArrayList<String> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + fileName + "\" not found");
        }

        return list;
    }

    public static void removeEvenNumbersFromList(ArrayList<Integer> list) {
        if (list == null) {
            throw new NullPointerException("List argument must be not null");
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 2 == 0) {
                list.remove(i);
                i--;
            }
        }
    }

    public static <T> ArrayList<T> getListWithoutDuplicates(ArrayList<T> list) {
        if (list == null) {
            throw new NullPointerException("List argument must be not null");
        }

        ArrayList<T> listWithoutDuplicates = new ArrayList<>(list.size());

        for (T item : list) {
            if (!listWithoutDuplicates.contains(item)) {
                listWithoutDuplicates.add(item);
            }
        }

        return listWithoutDuplicates;
    }

    public static void main(String[] args) {
        System.out.println("1) Чтение в список всех строк из файла");

        ArrayList<String> fileLinesList = readFileLinesToArrayList("input.txt");
        System.out.println("fileLinesList = " + fileLinesList);

        System.out.println();
        System.out.println("2) Удаление из списка всех четных чисел");

        ArrayList<Integer> listToRemoveEvenNumbers = new ArrayList<>(Arrays.asList(1, 2, 2, 3, 4, 5, 6, 7, 8, 8, 9));

        System.out.println("Исходный список до удаления четных чисел:");
        System.out.println(listToRemoveEvenNumbers);

        removeEvenNumbersFromList(listToRemoveEvenNumbers);

        System.out.println();
        System.out.println("Список после удаления четных чисел:");
        System.out.println(listToRemoveEvenNumbers);

        System.out.println();
        System.out.println("3) Создание списка без повторений");

        ArrayList<Integer> listWithDuplicates = new ArrayList<>(Arrays.asList(1, 5, 2, 1, 3, 5));
        ArrayList<Integer> listWithoutDuplicates = getListWithoutDuplicates(listWithDuplicates);

        System.out.println("Исходный список с повторениями = " + listWithDuplicates);
        System.out.println("Список без повторений = " + listWithoutDuplicates);
    }
}