package ru.academits.java.glushkov.list_main;

import ru.academits.java.glushkov.list.SinglyLinkedList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("1. Получение размера списка");

        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(Arrays.asList(14, 3, 5, 8, 11));

        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        System.out.println();
        System.out.println("2. Получение значения первого элемента");

        System.out.println("Значение первого элемента списка list: " + list.getFirst());

        System.out.println();
        System.out.println("3. Получение/изменение значения по указанному индексу");

        System.out.printf("list = %s; size = %d%n", list, list.getSize());
        System.out.println("Значение элемента списка list по индексу 4: " + list.get(4));

        System.out.println();
        System.out.println("После установки значения -7 по индекксу 1: ");

        Integer oldValue = list.set(1, -7);

        System.out.printf("list = %s; size = %d; старое значение по индексу 1: %d%n", list, list.getSize(), oldValue);

        System.out.println();
        System.out.println("4. Удаление элемента по индексу");

        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        Integer removedValue = list.remove(2);

        System.out.println("После удаления элемента по индексу 2: ");
        System.out.printf("list = %s; size = %d; значение удаленного элемента: %d%n", list, list.getSize(), removedValue);

        System.out.println();
        System.out.println("5. Вставка элемента в начало");

        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        list.insertFirst(23);

        System.out.println("После вставки элемента 23 в начало списка: ");
        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        System.out.println();
        System.out.println("6. Вставка элемента по индексу");

        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        list.insert(3, 13);

        System.out.println("После вставки элемента 13 по индексу 3");
        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        System.out.println();
        System.out.println("7. Удаление узла по значению");

        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        boolean isRemoved = list.removeByData(-7);

        System.out.println("После удаления узла со значением -7");
        System.out.printf("list = %s; size = %d; элемент был удален: %s%n", list, list.getSize(), isRemoved);

        isRemoved = list.removeByData(-9);

        System.out.println("После удаления узла со значением -9");
        System.out.printf("list = %s; size = %d; элемент был удален: %s%n", list, list.getSize(), isRemoved);

        System.out.println();
        System.out.println("8. Удаление первого элемента");

        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        removedValue = list.removeFirst();

        System.out.println("После удаления первого элемента");
        System.out.printf("list = %s; size = %d; значение удаленного элемента: %d%n", list, list.getSize(), removedValue);

        System.out.println();
        System.out.println("9. Разворот списка");

        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        list.reverse();

        System.out.printf("list = %s; size = %d%n", list, list.getSize());

        System.out.println();
        System.out.println("10. Копирование списка");

        SinglyLinkedList<Integer> listCopy = list.copy();

        System.out.printf("list = %s; size = %d%n", list, list.getSize());
        System.out.printf("copy = %s; size = %d%n", listCopy, listCopy.getSize());

        listCopy.set(1, -7);

        System.out.println("После установки в копии по индексу 1 значения -7");
        System.out.printf("list = %s; size = %d%n", list, list.getSize());
        System.out.printf("copy = %s; size = %d%n", listCopy, listCopy.getSize());
    }
}