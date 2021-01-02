package ru.academits.java.glushkov.graph_main;

import ru.academits.java.glushkov.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;

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
                {0, 0, 0, 0, 0, 917, 0, 0}};

        ArrayList<String> citiesNames = new ArrayList<>(Arrays.asList(
                "Омск",
                "Новосибирск",
                "Кемерово",
                "Павлодар",
                "Аделаида",
                "Сидней",
                "Мельбурн",
                "Брисбен"));

        Graph citiesGraph = new Graph(array, citiesNames);

        System.out.println("1) Обход графа в ширину");
        citiesGraph.walkInBreadth(p -> System.out.print(p + " "));

        System.out.println();
        System.out.println("2) Обход графа в глубину");
        citiesGraph.walkInDepth(p -> System.out.print(p + " "));
    }
}
