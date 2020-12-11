package ru.academits.java.glushkov.array_list_main;

import ru.academits.java.glushkov.array_list.MyArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        System.out.println("1) Тестирование конструкторов");
        System.out.println("a) Конструктор по умолчанию");

        MyArrayList<Integer> list1 = new MyArrayList<>();

        System.out.println("list1 = " + list1 + "; size = " + list1.size());

        System.out.println();
        System.out.println("b) Конструктор, принимающий начальную вместимость массива");

        MyArrayList<Integer> list2 = new MyArrayList<>(10);

        System.out.println("list2 = " + list2 + "; size = " + list2.size());

        System.out.println();
        System.out.println("с) Конструктор, принимающий коллекцию объектов");

        MyArrayList<Integer> list3 = new MyArrayList<>(Arrays.asList(1, 2, 3, 4, 2, 5, 2));
        MyArrayList<Integer> list4 = new MyArrayList<>(Arrays.asList(2, 3));

        System.out.println("list3 = " + list3 + "; size = " + list3.size());
        System.out.println("list4 = " + list4 + "; size = " + list4.size());

        System.out.println();
        System.out.println("2) Метод contains");
        System.out.println("list3 = " + list3 + "; size = " + list3.size());
        System.out.printf("list3 содержит %d: %s%n", 1, list3.contains(1));
        System.out.printf("list3 содержит %d: %s%n", 10, list3.contains(10));
        System.out.printf("list3 содержит %d: %s%n", 4, list3.contains(4));

        System.out.println();
        System.out.println("2) Метод iterator");
        System.out.println("Вывод элементов списка list3 с помощью итератора:");

        Iterator list3Iterator = list3.iterator();

        while (list3Iterator.hasNext()) {
            System.out.print(list3Iterator.next() + " ");
        }

        System.out.println();
        System.out.println("Вывод элементов списка list3 с помощью цикла for each:");

        for (Integer elem : list3) {
            System.out.print(elem + " ");
        }

        System.out.println();
        System.out.println("3) Метод toArray()");

        Object[] list3ObjectArray = list3.toArray();

        System.out.println(Arrays.toString(list3ObjectArray));

        System.out.println();
        System.out.println("4) Метод toArray(T1[] array)");

        Number[] array = new Number[list3.size() + 3];
        Arrays.fill(array, 1);

        Number[] list3NumberArray = list3.toArray(array);

        System.out.println(Arrays.toString(list3NumberArray));

        System.out.println();
        System.out.println("5) Метод add()");

        System.out.println("list4 = " + list4 + "; size = " + list4.size());

        list4.add(-7);
        list4.add(-3);

        System.out.println("После добавления элементов -7 и -3:");
        System.out.println("list4 = " + list4 + "; size = " + list4.size());

        System.out.println();
        System.out.println("6) Метод remove(Object o)");

        System.out.println("list4 = " + list4 + "; size = " + list4.size());

        list4.remove(Integer.valueOf(3));
        list4.remove(Integer.valueOf(-3));

        System.out.println("После удаления элементов 3 и -3:");
        System.out.println("list4 = " + list4 + "; size = " + list4.size());

        System.out.println();
        System.out.println("7) Метод containsAll");

        System.out.println("list3 = " + list3 + "; size = " + list3.size());

        ArrayList<Integer> list5 = new ArrayList<>(Arrays.asList(2, 3, 5));
        ArrayList<Integer> list6 = new ArrayList<>(Arrays.asList(2, 3, 5, 7));

        System.out.println("list5 = " + list5 + "; size = " + list5.size());
        System.out.println("list6 = " + list6 + "; size = " + list6.size());

        System.out.println("list3 содержит все элементы списка list5: " + list3.containsAll(list5));
        System.out.println("list3 содержит все элементы списка list6: " + list3.containsAll(list6));

        System.out.println();
        System.out.println("8) Метод addAll(int index, Collection<? extends T> c)");

        MyArrayList<Number> list7 = new MyArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        MyArrayList<Integer> list8 = new MyArrayList<>(Arrays.asList(-1, -2, -3));
        MyArrayList<Double> list9 = new MyArrayList<>(Arrays.asList(6.5, 7.5));

        System.out.println("list7 = " + list7 + "; size = " + list7.size());
        System.out.println("list8 = " + list8 + "; size = " + list8.size());
        System.out.println("list9 = " + list9 + "; size = " + list9.size());

        list7.addAll(0, list8);

        System.out.println("После добавления к списку list7 списка list8 по индексу 0");
        System.out.println("list7 = " + list7 + "; size = " + list7.size());

        list7.addAll(2, list9);

        System.out.println("После добавления к списку list7 списка list9 по индексу 2");
        System.out.println("list7 = " + list7 + "; size = " + list7.size());

        System.out.println();
        System.out.println("9) Метод removeAll(Collection<?> c)");

        System.out.println("list7 = " + list7 + "; size = " + list7.size());
        System.out.println("list8 = " + list8 + "; size = " + list8.size());
        System.out.println("list9 = " + list9 + "; size = " + list9.size());

        list7.removeAll(list8);

        System.out.println("После удаления из списка list7 элементов списка list8");
        System.out.println("list7 = " + list7 + "; size = " + list7.size());

        list7.removeAll(list9);
        list7.trimToSize();

        System.out.println("После удаления из списка list7 элементов списка list9");
        System.out.println("list7 = " + list7 + "; size = " + list7.size());

        System.out.println();
        System.out.println("10) Метод retainAll(Collection<?> c)");

        MyArrayList<Integer> list10 = new MyArrayList<>(Arrays.asList(1, 2, 1, 3, 4, 2, 1));
        MyArrayList<Integer> list11 = new MyArrayList<>(Arrays.asList(2, 4));

        System.out.println("list10 = " + list10 + "; size = " + list10.size());
        System.out.println("list11 = " + list11 + "; size = " + list11.size());

        list10.retainAll(list11);

        System.out.println("После удаления из списка list10 элементов, отсутствующих в списке list11");
        System.out.println("list10 = " + list10 + "; size = " + list10.size());

        System.out.println();
        System.out.println("11) Методы get, set, add, remove");

        MyArrayList<Integer> list12 = new MyArrayList<>(Arrays.asList(1, 3, 5, 6));

        System.out.println("list12 = " + list12 + "; size = " + list12.size());
        System.out.println("Элемент списка list12 по индексу 2: " + list12.get(2));

        list12.set(1, 7);

        System.out.println();
        System.out.println("После установки в списке list12 значения 7 по индексу 1:");
        System.out.println("list12 = " + list12 + "; size = " + list12.size());

        list12.add(2, -1);

        System.out.println();
        System.out.println("После добавления в список list12 элемента -1 по индексу 2:");
        System.out.println("list12 = " + list12 + "; size = " + list12.size());

        list12.remove(3);

        System.out.println();
        System.out.println("После удаления из списка list12 элемента по индексу 3:");
        System.out.println("list12 = " + list12 + "; size = " + list12.size());

        System.out.println();
        System.out.println("12) Методы indexOf, lastIndexOf");

        MyArrayList<Integer> list13 = new MyArrayList<>(Arrays.asList(6, 4, 8, 2, 9, 4, 2, 9, 3));

        System.out.println("list13 = " + list13 + "; size = " + list13.size());

        System.out.println("Индекс первого вхождения элемента 2 в список list13: " + list13.indexOf(2));
        System.out.println("Индекс последнего вхождения элемента 2 в список list13: " + list13.lastIndexOf(2));
        System.out.println("Индекс первого вхождения элемента -3 в список list13: " + list13.indexOf(-3));
        System.out.println("Индекс последнего вхождения элемента -3 в список list13: " + list13.lastIndexOf(-3));
    }
}