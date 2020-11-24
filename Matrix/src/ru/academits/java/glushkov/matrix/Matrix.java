package ru.academits.java.glushkov.matrix;

import ru.academits.java.glushkov.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] matrix;

    public Matrix(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Matrix dimensions must be positive integers");
        }

        matrix = new Vector[n];

        for (int i = 0; i < n; i++) {
            matrix[i] = new Vector(m);
        }
    }

    public Matrix(Matrix m) {
        matrix = new Vector[m.getSize()[0]];

        for (int i = 0; i < m.getSize()[0]; i++) {
            matrix[i] = m.getRowAt(i);
        }
    }

    public Matrix(double[][] array) {
        int rowsCount = array.length;

        int maxRowLength = array[0].length;

        for (int i = 1; i < rowsCount; i++) {
            if (array[i].length > maxRowLength) {
                maxRowLength = array[i].length;
            }
        }

        matrix = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            matrix[i] = new Vector(maxRowLength, array[i]);
        }
    }

    public Matrix(Vector[] array) {
        int rowsCount = array.length;

        int maxRowLength = array[0].getSize();

        for (int i = 1; i < rowsCount; i++) {
            if (array[i].getSize() > maxRowLength) {
                maxRowLength = array[i].getSize();
            }
        }

        matrix = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            matrix[i] = new Vector(maxRowLength);
            matrix[i].add(array[i]);
        }
    }

    public int[] getSize() {
        return new int[]{matrix.length, matrix[0].getSize()};
    }

    @Override
    public String toString() {
        return Arrays.toString(matrix);
    }

    public Vector getRowAt(int index) {
        if (index > this.getSize()[0] - 1) {
            throw new IllegalArgumentException("Index must not exceed the number of rows in matrix minus 1");
        }

        return matrix[index];
    }

    public void setRowAt(int index, Vector vector) {
        if (index > this.getSize()[0] - 1) {
            throw new IllegalArgumentException("Index must not exceed the number of rows in matrix minus 1");
        }

        if (vector.getSize() != this.getSize()[1]) {
            throw new IllegalArgumentException("Argument vector's size must be equal to number of columns in matrix");
        }

        matrix[index] = vector;
    }

    public Vector getColumnAt(int index) {
        if (index > this.getSize()[1] - 1) {
            throw new IllegalArgumentException("Index must not exceed the number of columns in matrix minus 1");
        }

        Vector column = new Vector(this.getSize()[0]);

        for (int i = 0; i < this.getSize()[0]; i++) {
            column.setCoordinate(i, matrix[i].getCoordinate(index));
        }

        return column;
    }

    public void transpose() {
        int columnsCount = this.getSize()[1];
        Vector[] columns = new Vector[columnsCount];

        for (int i = 0; i < columnsCount; i++) {
            columns[i] = this.getColumnAt(i);
        }

        matrix = columns;
    }

    public void multiplyBy(double number) {
        int rowsCount = this.getSize()[0];

        for (int i = 0; i < rowsCount; i++) {
            matrix[i].multiplyByScalar(number);
        }
    }

    public double getDeterminant() {
        if (this.getSize()[0] != this.getSize()[1]) {
            throw new UnsupportedOperationException("Determinant calculation is possible only for square matrices.");
        }

        int sign = 1;
        int size = this.getSize()[0];

        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = this.matrix[i].getCoordinate(j);
            }
        }

        double epsilon = 1e-30;

        for (int i = 0; i < size - 1; i++) {
            double maxElementInCurrentColumn = Math.abs(matrix[i][i]);
            int maxElementIndex = i;

            for (int j = i + 1; j < size; j++) {
                double currentElement = Math.abs(matrix[j][i]);

                if (currentElement > maxElementInCurrentColumn) {
                    maxElementInCurrentColumn = currentElement;
                    maxElementIndex = j;
                }
            }

            if (maxElementInCurrentColumn < epsilon) {
                return 0;
            }

            if (maxElementIndex != i) {
                for (int j = i; j < size; j++) {
                    double temp = matrix[i][j];
                    matrix[i][j] = matrix[maxElementIndex][j];
                    matrix[maxElementIndex][j] = temp;
                }

                sign = -sign;
            }

            for (int j = i + 1; j < size; j++) {
                double coefficient = matrix[j][i] / matrix[i][i];

                matrix[j][i] = 0;
                for (int k = i + 1; k < size; k++) {
                    matrix[j][k] -= coefficient * matrix[i][k];
                }
            }
        }

        double determinant = matrix[0][0];

        for (int i = 1; i < size; i++) {
            determinant *= matrix[i][i];
        }

        return sign * determinant;
    }

    public Vector multiplyBy(Vector vector) {
        int columnsCount = this.getSize()[1];

        if (vector.getSize() != columnsCount) {
            throw new IllegalArgumentException("Argument vector's size must be equal to number of columns in matrix");
        }

        int rowsCount = this.getSize()[0];
        Vector result = new Vector(rowsCount);

        for (int i = 0; i < rowsCount; i++) {
            result.setCoordinate(i, Vector.getScalarProduct(matrix[i], vector));
        }

        return result;
    }

    public void add(Matrix m) {
        if (m.getSize()[0] != this.getSize()[0] || m.getSize()[1] != this.getSize()[1]) {
            throw new IllegalArgumentException("Size of matrix to be added must coincide with " +
                    "the current matrix size.");
        }

        int rowsCount = this.getSize()[0];

        for (int i = 0; i < rowsCount; i++) {
            matrix[i].add(m.getRowAt(i));
        }
    }

    public void subtract(Matrix m) {
        if (m.getSize()[0] != this.getSize()[0] || m.getSize()[1] != this.getSize()[1]) {
            throw new IllegalArgumentException("Size of matrix to be subtracted must coincide with " +
                    "the current matrix size.");
        }

        int rowsCount = this.getSize()[0];

        for (int i = 0; i < rowsCount; i++) {
            matrix[i].subtract(m.getRowAt(i));
        }
    }

    public static Matrix getSum(Matrix m1, Matrix m2) {
        if (m1.getSize()[0] != m2.getSize()[0] || m1.getSize()[1] != m2.getSize()[1]) {
            throw new IllegalArgumentException("Sizes of matrices to be summed must coincide.");
        }

        Matrix result = new Matrix(m1);
        result.add(m2);

        return result;
    }

    public static Matrix getDifference(Matrix m1, Matrix m2) {
        if (m1.getSize()[0] != m2.getSize()[0] || m1.getSize()[1] != m2.getSize()[1]) {
            throw new IllegalArgumentException("Sizes of matrices for which difference to be calculated " +
                    "must coincide .");
        }

        Matrix result = new Matrix(m1);
        result.subtract(m2);

        return result;
    }

    public static Matrix getProduct(Matrix m1, Matrix m2) {
        if (m1.getSize()[1] != m2.getSize()[0]) {
            throw new IllegalArgumentException("The number of columns in the first matrix must coincide with " +
                    "the number of rows int the second matrix.");
        }

        Matrix result = new Matrix(m1.getSize()[0], m2.getSize()[1]);

        for (int i = 0; i < m1.getSize()[0]; i++) {
            Vector currentRow = m1.getRowAt(i);

            for (int j = 0; j < m2.getSize()[1]; j++) {
                Vector currentColumn = m2.getColumnAt(j);
                result.getRowAt(i).setCoordinate(j, Vector.getScalarProduct(currentRow, currentColumn));
            }
        }

        return result;
    }
}
