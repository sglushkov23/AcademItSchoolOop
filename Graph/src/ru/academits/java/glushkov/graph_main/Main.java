package ru.academits.java.glushkov.graph_main;

import ru.academits.java.glushkov.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        int[][] array = {
                {0, 681, 0, 413, 0, 0, 0, 0},
                {681, 0, 274, 589, 0, 0, 0, 0},
                {0, 274, 0, 0, 0, 0, 0, 0},
                {413, 589, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1375, 727, 0},
                {0, 0, 0, 0, 1375, 0, 878, 917},
                {0, 0, 0, 0, 727, 878, 0, 0},
                {0, 0, 0, 0, 0, 917, 0, 0}
        };

        ArrayList<String> citiesNames = new ArrayList<>(Arrays.asList(
                "Омск",
                "Новосибирск",
                "Кемерово",
                "Павлодар",
                "Аделаида",
                "Сидней",
                "Мельбурн",
                "Брисбен")
        );

        System.out.println("1. Использование конструктора с двумя аргументами");

        Graph<String> citiesGraph = new Graph<>(array, citiesNames);
        Consumer<String> action = p -> System.out.print(p.toUpperCase() + " ");

        System.out.println("1) Обход графа в ширину");
        citiesGraph.walkInBreadth(action);

        System.out.println();
        System.out.println("2) Обход графа в глубину");
        citiesGraph.walkInDepth(action);

        System.out.println();
        System.out.println("3) Обход графа в глубину с рекурсией");
        citiesGraph.walkInDepthRecursive(action);

        System.out.println();
        System.out.println();
        System.out.println("2. Использование конструктора с одним аргументом");

        Graph<String> citiesGraph2 = new Graph<>(array);

        System.out.println("1) Обход графа в ширину");
        citiesGraph2.walkInBreadth(null);

        System.out.println();
        System.out.println("2) Обход графа в глубину");
        citiesGraph2.walkInDepth(null);

        System.out.println();
        System.out.println("3) Обход графа в глубину с рекурсией");
        citiesGraph2.walkInDepthRecursive(null);
    }
}