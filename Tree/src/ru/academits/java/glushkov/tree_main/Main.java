package ru.academits.java.glushkov.tree_main;

import ru.academits.java.glushkov.tree.BinaryTree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        System.out.println("1) Конструктор без компаратора");

        BinaryTree<Integer> tree1 = new BinaryTree<>(Arrays.asList(3, 1, 2, 4, 6));
        System.out.printf("tree1 = %s; size = %d%n", tree1, tree1.getSize());

        System.out.println();
        System.out.println("2) Конструктор с компаратором");

        Comparator<Integer> comparator = (o1, o2) -> {
            if (o1 == null && o2 != null) {
                return 1;
            }

            if (o1 != null && o2 == null) {
                return -1;
            }

            if (o1 == null) {
                return 0;
            }

            return o1.compareTo(o2);
        };

        BinaryTree<Integer> tree2 = new BinaryTree<>(Arrays.asList(3, 1, 2, 4, 6, null), comparator);
        System.out.printf("tree2 = %s; size = %d%n", tree2, tree2.getSize());

        System.out.println();
        System.out.println("3) Вставка элемента");

        BinaryTree<Integer> tree3 = new BinaryTree<>(comparator);
        List<Integer> list = Arrays.asList(8, 3, 10, 1, 6, 14, 4, 7, 13, null);

        System.out.printf("tree3 = %s; size = %d%n", tree3, tree3.getSize());
        System.out.printf("list = %s; size = %d%n", list, list.size());

        for (Integer item : list) {
            tree3.add(item);
        }

        System.out.println();
        System.out.println("После вставки элементов списка list в дерево:");
        System.out.printf("tree3 = %s; size = %d%n", tree3, tree3.getSize());

        System.out.println();
        System.out.println("4) Поиск узла");

        System.out.println("Дерево tree3 содержит узел 3: " + tree3.contains(3));
        System.out.println("Дерево tree3 содержит узел 4: " + tree3.contains(4));
        System.out.println("Дерево tree3 содержит узел 5: " + tree3.contains(5));
        System.out.println("Дерево tree3 содержит узел null: " + tree3.contains(null));

        System.out.println();
        System.out.println("5) Удаление первого вхождения узла по значению");

        System.out.printf("tree3 = %s; size = %d%n", tree3, tree3.getSize());

        boolean isRemoved = tree3.remove(8);

        System.out.println();
        System.out.println("После удаления узла 8:");
        System.out.println("Узел 8 был удален: " + isRemoved);
        System.out.printf("tree3 = %s; size = %d%n", tree3, tree3.getSize());

        isRemoved = tree3.remove(3);

        System.out.println();
        System.out.println("После удаления узла 3:");
        System.out.println("Узел 3 был удален: " + isRemoved);
        System.out.printf("tree3 = %s; size = %d%n", tree3, tree3.getSize());

        isRemoved = tree3.remove(null);

        System.out.println();
        System.out.println("После удаления узла null:");
        System.out.println("Узел null был удален: " + isRemoved);
        System.out.printf("tree3 = %s; size = %d%n", tree3, tree3.getSize());

        isRemoved = tree3.remove(-5);

        System.out.println();
        System.out.println("После удаления узла -5:");
        System.out.println("Узел -5 был удален: " + isRemoved);
        System.out.printf("tree3 = %s; size = %d%n", tree3, tree3.getSize());

        System.out.println();
        System.out.println("6) Обход в ширину");

        Consumer<Number> action = p -> System.out.print(p + " ");

        tree3.walkInBreadth(action);

        System.out.println();
        System.out.println("7) Обход в глубину без рекурсии");

        tree3.walkInDepth(action);

        System.out.println();
        System.out.println("8) Обход в глубину с рекурсией");

        tree3.walkInDepthRecursive(action);
    }
}