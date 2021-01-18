package ru.academits.java.glushkov.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class Graph<E> {
    private final int[][] adjacencyArray;
    private final int size;
    private final ArrayList<E> verticesData;

    public Graph(int[][] adjacencyArray) {
        this(adjacencyArray, null);
    }

    public Graph(int[][] adjacencyArray, ArrayList<E> verticesData) {
        if (adjacencyArray == null) {
            throw new NullPointerException("Constructor's array argument must be not null");
        }

        int rowsCount = adjacencyArray.length;

        if (rowsCount == 0) {
            throw new IllegalArgumentException("Constructor's array argument length must be nonzero");
        }

        for (int[] row : adjacencyArray) {
            if (row == null) {
                throw new NullPointerException("Elements of constructor's array argument must be not null");
            }
        }

        int columnsCount = adjacencyArray[0].length;

        if (rowsCount != columnsCount) {
            throw new IllegalArgumentException(String.format(
                    "Rows number must be equal to columns number in Constructor's array argument: rows number = %d, columns number = %d",
                    rowsCount, columnsCount));
        }

        for (int i = 1; i < rowsCount; i++) {
            if (adjacencyArray[i].length != columnsCount) {
                throw new IllegalArgumentException(String.format(
                        "All rows in array argument must have the same length: rows from 0 to %d have length %d; row %d has length %d",
                        i - 1, columnsCount, i, adjacencyArray[i].length));
            }
        }

        this.adjacencyArray = new int[rowsCount][];
        size = rowsCount;
        int i = 0;

        for (int[] row : adjacencyArray) {
            this.adjacencyArray[i] = Arrays.copyOf(row, size);
            i++;
        }

        if (verticesData == null) {
            this.verticesData = null;

            return;
        }

        if (size != verticesData.size()) {
            throw new IllegalArgumentException(String.format(
                    "Argument verticesData size must be equal to graph size: verticesData size = %d; graph size = %d",
                    verticesData.size(), size));
        }

        this.verticesData = new ArrayList<>(verticesData);
    }

    public void walkInBreadth(Consumer<? super E> action) {
        Queue<Integer> queue = new LinkedList<>();

        boolean[] visited = new boolean[size];

        int visitedCount = 0;
        int nextIndexInVisitedArray = 0;

        while (visitedCount != size) {
            for (int i = nextIndexInVisitedArray; i < size; i++) {
                if (!visited[i]) {
                    queue.add(i);
                    nextIndexInVisitedArray = i + 1;
                    break;
                }
            }

            while (!queue.isEmpty()) {
                int currentVertex = queue.remove();

                if (!visited[currentVertex]) {
                    makeActionForVertex(currentVertex, action);

                    for (int i = 0; i < size; i++) {
                        int element = adjacencyArray[currentVertex][i];

                        if (element != 0 && !visited[i]) {
                            queue.add(i);
                        }
                    }

                    visited[currentVertex] = true;
                    visitedCount++;
                }
            }
        }
    }

    public void walkInDepth(Consumer<? super E> action) {
        ArrayList<Integer> stack = new ArrayList<>();

        boolean[] visited = new boolean[size];

        int visitedCount = 0;
        int nextIndexInVisitedArray = 0;

        while (visitedCount != size) {
            for (int i = nextIndexInVisitedArray; i < size; i++) {
                if (!visited[i]) {
                    stack.add(i);
                    nextIndexInVisitedArray = i + 1;
                    break;
                }
            }

            while (!stack.isEmpty()) {
                int currentVertex = stack.remove(stack.size() - 1);

                if (!visited[currentVertex]) {
                    makeActionForVertex(currentVertex, action);

                    for (int i = size - 1; i >= 0; i--) {
                        int element = adjacencyArray[currentVertex][i];

                        if (element != 0 && !visited[i]) {
                            stack.add(i);
                        }
                    }

                    visited[currentVertex] = true;
                    visitedCount++;
                }
            }
        }
    }

    public void walkInDepthRecursive(Consumer<? super E> action) {
        boolean[] visited = new boolean[size];
        boolean hasNotVisitedVertices = true;
        int nextIndexInVisitedArray = 0;
        int i;

        while (hasNotVisitedVertices) {
            for (i = nextIndexInVisitedArray; i < size; i++) {
                if (!visited[i]) {
                    nextIndexInVisitedArray = i + 1;
                    break;
                }

                hasNotVisitedVertices = false;
            }

            visit(i, action, visited);
        }
    }

    private void visit(int vertexIndex, Consumer<? super E> action, boolean[] visited) {
        if (visited[vertexIndex]) {
            return;
        }

        makeActionForVertex(vertexIndex, action);
        visited[vertexIndex] = true;

        for (int i = 0; i < size; i++) {
            int element = adjacencyArray[vertexIndex][i];

            if (element != 0 && !visited[i]) {
                visit(i, action, visited);
            }
        }
    }

    private void makeActionForVertex(int vertexIndex, Consumer<? super E> action) {
        if (verticesData == null) {
            System.out.print(vertexIndex + " ");
        } else if (action == null) {
            System.out.print(verticesData.get(vertexIndex) + " ");
        } else {
            action.accept(verticesData.get(vertexIndex));
        }
    }
}