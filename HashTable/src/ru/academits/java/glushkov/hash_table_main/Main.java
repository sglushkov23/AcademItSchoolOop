package ru.academits.java.glushkov.hash_table_main;

import ru.academits.java.glushkov.array_list.MyArrayList;
import ru.academits.java.glushkov.hash_table.HashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        System.out.println("1. Тестирование конструкторов");
        System.out.println("a) Конструктор без аргументов");

        HashTable<Integer> hashTable = new HashTable<>();

        System.out.printf("hashTable = %s; size = %d%n", hashTable, hashTable.size());

        System.out.println();
        System.out.println("b) Конструктор, принимающий коллекцию в качестве аргумента");

        HashTable<Integer> hashTable2 = new HashTable<>(Arrays.asList(31, 12, 13, 47, 26, 5, 29));

        System.out.printf("hashTable2 = %s; size = %d%n", hashTable2, hashTable2.size());

        System.out.println();
        System.out.println("2) Метод contains");
        System.out.printf("hashTable2 = %s; size = %d%n", hashTable2, hashTable2.size());
        System.out.printf("hashTable2 содержит %d: %s%n", 13, hashTable2.contains(13));
        System.out.printf("hashTable2 содержит %d: %s%n", 17, hashTable2.contains(17));
        System.out.printf("hashTable2 содержит %d: %s%n", 26, hashTable2.contains(26));

        System.out.println();
        System.out.println("3) Методы iterator и add");

        HashTable<Integer> hashTable3 = new HashTable<>(7);

        for (int i = 0; i < 15; i++) {
            hashTable3.add(i * 3);
        }

        System.out.printf("hashTable3 = %s; size = %d%n", hashTable3, hashTable3.size());

        Iterator iterator = hashTable3.iterator();

        System.out.println("Вывод элементов хэш-таблицы с помощью while");

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        System.out.println();
        System.out.println("Вывод элементов хэш-таблицы с помощью foreach");

        for (Integer item : hashTable3) {
            System.out.print(item + " ");
        }

        System.out.println();
        System.out.println();
        System.out.println("4) Метод toArray()");

        Object[] hashTable3ObjectArray = hashTable3.toArray();

        System.out.printf("hashTable3 = %s; size = %d%n", hashTable3, hashTable3.size());
        System.out.println("hashTable3ObjectArray = " + Arrays.toString(hashTable3ObjectArray));

        System.out.println();
        System.out.println("5) Метод toArray(T1[] array)");

        Number[] array = new Number[hashTable3.size() + 3];
        Arrays.fill(array, 1);

        Number[] hashTable3NumberArray = hashTable3.toArray(array);

        System.out.printf("hashTable3 = %s; size = %d%n", hashTable3, hashTable3.size());
        System.out.println("hashTable3NumberArray = " + Arrays.toString(hashTable3NumberArray));

        System.out.println();
        System.out.println("6) Метод remove(Object o)");

        HashTable<Integer> hashTable4 = new HashTable<>(Arrays.asList(1, 2, 3, 4, 2, 5, 2));

        System.out.printf("hashTable4 = %s; size = %d%n", hashTable4, hashTable4.size());

        System.out.println("Элемент 2 был удален из хэш-таблицы: " + hashTable4.remove(2));
        System.out.println("Элемент 3 был удален из хэш-таблицы: " + hashTable4.remove(3));
        System.out.println("Элемент -5 был удален из хэш-таблицы: " + hashTable4.remove(-5));

        System.out.println("После удаления элементов 2, 3 и -5:");
        System.out.printf("hashTable4 = %s; size = %d%n", hashTable4, hashTable4.size());

        System.out.println();
        System.out.println("7) Метод containsAll");

        System.out.printf("hashTable4 = %s; size = %d%n", hashTable4, hashTable4.size());

        ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(2, 4, 5));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(2, 4, 5, 7));

        System.out.println("list1 = " + list1 + "; size = " + list1.size());
        System.out.println("list2 = " + list2 + "; size = " + list2.size());

        System.out.println("hashTable4 содержит все элементы списка list1: " + hashTable4.containsAll(list1));
        System.out.println("hashTable4 содержит все элементы списка list2: " + hashTable4.containsAll(list2));

        System.out.println();
        System.out.println("8) Метод addAll(int index, Collection<? extends T> c)");

        HashTable<Number> hashTable5 = new HashTable<>(Arrays.asList(1, 2, 3, 4, 5));
        MyArrayList<Integer> list3 = new MyArrayList<>(Arrays.asList(-1, -2, -3, 2, 5));
        MyArrayList<Double> list4 = new MyArrayList<>(Arrays.asList(6.5, 7.5));

        System.out.printf("hashTable5 = %s; size = %d%n", hashTable5, hashTable5.size());
        System.out.println("list3 = " + list3 + "; size = " + list3.size());
        System.out.println("list4 = " + list4 + "; size = " + list4.size());

        hashTable5.addAll(list3);

        System.out.println();
        System.out.println("После добавления к хэш-таблице hashTable5 списка list3");
        System.out.printf("hashTable5 = %s; size = %d%n", hashTable5, hashTable5.size());

        hashTable5.addAll(list4);

        System.out.println();
        System.out.println("После добавления к хэш-таблице hashTable5 списка list4");
        System.out.printf("hashTable5 = %s; size = %d%n", hashTable5, hashTable5.size());

        System.out.println();
        System.out.println("9) Метод removeAll(Collection<?> c)");

        System.out.printf("hashTable5 = %s; size = %d%n", hashTable5, hashTable5.size());
        System.out.println("list3 = " + list3 + "; size = " + list3.size());
        System.out.println("list4 = " + list4 + "; size = " + list4.size());

        hashTable5.removeAll(list3);

        System.out.println();
        System.out.println("После удаления из хэш-таблицы hashTable5 элементов списка list3");
        System.out.printf("hashTable5 = %s; size = %d%n", hashTable5, hashTable5.size());

        hashTable5.removeAll(list4);

        System.out.println();
        System.out.println("После удаления из хэш-таблицы hashTable5 элементов списка list4");
        System.out.printf("hashTable5 = %s; size = %d%n", hashTable5, hashTable5.size());

        System.out.println();
        System.out.println("10) Метод retainAll(Collection<?> c)");

        HashTable<Integer> hashTable6 = new HashTable<>(Arrays.asList(1, 2, 1, 3, 4, 2, 1));
        HashTable<Integer> hashTable7 = new HashTable<>(Arrays.asList(1, 3));

        System.out.printf("hashTable6 = %s; size = %d%n", hashTable6, hashTable6.size());
        System.out.printf("hashTable7 = %s; size = %d%n", hashTable7, hashTable7.size());

        hashTable6.retainAll(hashTable7);

        System.out.println();
        System.out.println("После удаления из хэш-таблицы hashTable6 элементов, отсутствующих в хэш-таблице hashTable7");
        System.out.printf("hashTable6 = %s; size = %d%n", hashTable6, hashTable6.size());

        System.out.println();
        System.out.println("11) Метод clear");

        HashTable<Integer> hashTable9 = new HashTable<>(5);

        for (int i = 0; i < 12; i++) {
            hashTable9.add(i * 3);
        }

        System.out.printf("hashTable9 = %s; size = %d%n", hashTable9, hashTable9.size());

        hashTable9.clear();

        System.out.println("После вызова метода clear");
        System.out.printf("hashTable9 = %s; size = %d%n", hashTable9, hashTable9.size());

        System.out.println();
        System.out.println("12) Метод rebuild (не является методом интерфейса Collection)");

        HashTable<Integer> hashTable10 = new HashTable<>(5);

        for (int i = 0; i < 20; i++) {
            hashTable10.add(i * 3);
        }

        System.out.printf("hashTable10 = %s; size = %d%n", hashTable10, hashTable10.size());

        hashTable10.rebuild(hashTable10.size());

        System.out.println("После перестройки хэш-таблицы");
        System.out.printf("hashTable10 = %s; size = %d%n", hashTable10, hashTable10.size());
    }
}