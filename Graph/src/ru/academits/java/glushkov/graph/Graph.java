package ru.academits.java.glushkov.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class Graph {
    private final int[][] adjacencyArray;
    private final int size;
    private ArrayList<String> verticesNames;

    public Graph(int[][] array) {
        if (array == null) {
            throw new NullPointerException("Constructor's array argument must be not null");
        }

        int rowsCount = array.length;

        if (rowsCount == 0) {
            throw new IllegalArgumentException("Constructor's array argument length must be nonzero");
        }

        for (int[] row : array) {
            if (row == null) {
                throw new NullPointerException("Elements of constructor's array argument must be not null");
            }
        }

        int columnsCount = array[0].length;

        if (rowsCount != columnsCount) {
            throw new IllegalArgumentException(String.format(
                    "Rows number must be equal to columns number in Constructor's array argument: rows number = %d, columns number = %d",
                    rowsCount, columnsCount));
        }

        for (int i = 1; i < rowsCount; i++) {
            if (array[i].length != columnsCount) {
                throw new IllegalArgumentException(String.format(
                        "All rows in array argument must have the same length: rows from 0 to %d have length %d; row %d has length %d",
                        i - 1, columnsCount, i, array[i].length));
            }
        }

        adjacencyArray = new int[rowsCount][];
        size = rowsCount;
        int i = 0;

        for (int[] row : array) {
            adjacencyArray[i] = Arrays.copyOf(row, size);
            i++;
        }
    }

    public Graph(int[][] array, ArrayList<String> verticesNames) {
        this(array);

        if (verticesNames == null) {
            throw new IllegalArgumentException("Constructor's argument verticesData must be not null");
        }

        if (size != verticesNames.size()) {
            throw new IllegalArgumentException(String.format(
                    "Argument verticesData size must be equal to graph size: verticesData size = %d; graph size = %d",
                    verticesNames.size(), size));
        }

        this.verticesNames = new ArrayList<>(verticesNames);
    }

    public void walkInBreadth(Consumer<String> action) {
        Queue<Integer> queue = new LinkedList<>();

        boolean[] visited = new boolean[size];
        Arrays.fill(visited, false);

        int visitedCount = 0;

        while (visitedCount != size) {
            for (int i = 0; i < size; i++) {
                if (!visited[i]) {
                    queue.add(i);
                    break;
                }
            }

            while (!queue.isEmpty()) {
                int currentVertex = queue.remove();

                if (!visited[currentVertex]) {
                    if (verticesNames == null) {
                        action.accept(Integer.valueOf(currentVertex).toString());
                    } else {
                        action.accept(verticesNames.get(currentVertex));
                    }

                    visited[currentVertex] = true;
                    visitedCount++;
                }

                for (int j = 0; j < size; j++) {
                    int element = adjacencyArray[currentVertex][j];

                    if (element != 0 && !visited[j]) {
                        queue.add(j);
                    }
                }
            }
        }
    }

    public void walkInDepth(Consumer<String> action) {
        ArrayList<Integer> stack = new ArrayList<>();

        boolean[] visited = new boolean[size];
        Arrays.fill(visited, false);

        int visitedCount = 0;

        while (visitedCount != size) {
            for (int i = 0; i < size; i++) {
                if (!visited[i]) {
                    stack.add(i);
                    break;
                }
            }

            while (!stack.isEmpty()) {
                int currentVertex = stack.remove(stack.size() - 1);

                if (!visited[currentVertex]) {
                    if (verticesNames == null) {
                        action.accept(Integer.valueOf(currentVertex).toString());
                    } else {
                        action.accept(verticesNames.get(currentVertex));
                    }

                    visited[currentVertex] = true;
                    visitedCount++;
                }

                for (int j = size - 1; j >= 0; j--) {
                    int element = adjacencyArray[currentVertex][j];

                    if (element != 0 && !visited[j]) {
                        stack.add(j);
                    }
                }
            }
        }
    }
}