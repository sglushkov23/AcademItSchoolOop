package ru.academits.java.glushkov.matrix;

import ru.academits.java.glushkov.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] matrixRows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new IllegalArgumentException(String.format("Matrix dimensions must be positive integers: " +
                    "rowsCount = %d; columnsCount = %d", rowsCount, columnsCount));
        }

        matrixRows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            matrixRows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        int rowsCount = matrix.getRowsCount();
        matrixRows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            matrixRows[i] = matrix.getRowAt(i);
        }
    }

    public Matrix(double[][] array) {
        if (array == null) {
            throw new NullPointerException("Constructor's array argument must be not null");
        }

        int rowsCount = array.length;

        if (rowsCount == 0) {
            throw new IllegalArgumentException("Constructor's array argument length must be nonzero");
        }

        for (double[] element : array) {
            if (element == null) {
                throw new NullPointerException("Elements of constructor's array argument must be not null");
            }
        }

        int maxRowLength = array[0].length;

        for (int i = 1; i < rowsCount; i++) {
            if (array[i].length > maxRowLength) {
                maxRowLength = array[i].length;
            }
        }

        matrixRows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            matrixRows[i] = new Vector(maxRowLength, array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors == null) {
            throw new NullPointerException("Constructor's vectors argument must be not null");
        }

        int rowsCount = vectors.length;

        if (rowsCount == 0) {
            throw new IllegalArgumentException("Constructor's vectors argument length must be nonzero");
        }

        for (Vector vector : vectors) {
            if (vector == null) {
                throw new NullPointerException("Elements of constructor's vectors argument must be not null");
            }
        }

        int maxRowLength = vectors[0].getSize();

        for (int i = 1; i < rowsCount; i++) {
            if (vectors[i].getSize() > maxRowLength) {
                maxRowLength = vectors[i].getSize();
            }
        }

        matrixRows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            matrixRows[i] = new Vector(maxRowLength);
            matrixRows[i].add(vectors[i]);
        }
    }

    public int getRowsCount() {
        return matrixRows.length;
    }

    public int getColumnsCount() {
        return matrixRows[0].getSize();
    }

    @Override
    public String toString() {
        return Arrays.toString(matrixRows).replace("[", "{").replace("]", "}");
    }

    public String sizeToString() {
        return String.format("[%d, %d]", getRowsCount(), getColumnsCount());
    }

    public Vector getRowAt(int index) {
        if (index < 0 || index >= getRowsCount()) {
            throw new IndexOutOfBoundsException("Index must be non-negative and must be less than the number of rows in matrix: " +
                    "index = " + index);
        }

        return new Vector(matrixRows[index]);
    }

    public void setRowAt(int index, Vector vector) {
        if (index < 0 || index >= getRowsCount()) {
            throw new IndexOutOfBoundsException("Index must be non-negative and must be less than the number of rows in matrix: " +
                    "index = " + index);
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Argument vector's size must be equal to number of columns in matrix: vector = " + vector);
        }

        matrixRows[index] = new Vector(vector);
    }

    public Vector getColumnAt(int index) {
        if (index < 0 || index >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("Index must be non-negative and must be less than the number of columns in matrix: " +
                    "index = " + index);
        }

        int rowsCount = getRowsCount();
        Vector column = new Vector(rowsCount);

        for (int i = 0; i < rowsCount; i++) {
            column.setCoordinate(i, matrixRows[i].getCoordinate(index));
        }

        return column;
    }

    public void transpose() {
        int columnsCount = getColumnsCount();
        Vector[] columns = new Vector[columnsCount];

        for (int i = 0; i < columnsCount; i++) {
            columns[i] = getColumnAt(i);
        }

        matrixRows = columns;
    }

    public void multiplyBy(double number) {
        for (Vector row : matrixRows) {
            row.multiplyByScalar(number);
        }
    }

    public double getDeterminant() {
        if (getRowsCount() != getColumnsCount()) {
            throw new UnsupportedOperationException("Determinant calculation is possible only for square matrices: " +
                    "current matrix size = " + sizeToString());
        }

        int sign = 1;
        int size = getRowsCount();

        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = matrixRows[i].getCoordinate(j);
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
        int columnsCount = getColumnsCount();

        if (vector.getSize() != columnsCount) {
            throw new IllegalArgumentException(String.format("Argument vector's size must be equal to number of columns in matrix: " +
                    "matrix size = %s; vector size = %d", sizeToString(), vector.getSize()));
        }

        Vector result = new Vector(getRowsCount());
        int i = 0;

        for (Vector row : matrixRows) {
            result.setCoordinate(i, Vector.getScalarProduct(row, vector));
            i++;
        }

        return result;
    }

    public void add(Matrix matrix) {
        if (haveDifferentSizes(this, matrix)) {
            throw new IllegalArgumentException(String.format("Size of matrix to be added must coincide with the current matrix size: " +
                    "current matrix size = %s; added matrix size = %s", sizeToString(), matrix.sizeToString()));
        }

        int rowsCount = getRowsCount();

        for (int i = 0; i < rowsCount; i++) {
            matrixRows[i].add(matrix.getRowAt(i));
        }
    }

    public void subtract(Matrix matrix) {
        if (haveDifferentSizes(this, matrix)) {
            throw new IllegalArgumentException(String.format("Size of matrix to be subtracted must coincide with the current matrix size: " +
                    "current matrix size = %s; subtracted matrix size = %s", sizeToString(), matrix.sizeToString()));
        }

        int rowsCount = getRowsCount();

        for (int i = 0; i < rowsCount; i++) {
            matrixRows[i].subtract(matrix.getRowAt(i));
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (haveDifferentSizes(matrix1, matrix2)) {
            throw new IllegalArgumentException(String.format("Sizes of matrices to be summed must coincide:" +
                    "matrix1 size = %s, matrix2 size = %s", matrix1.sizeToString(), matrix2.sizeToString()));
        }

        Matrix result = new Matrix(matrix1);
        result.add(matrix2);

        return result;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (haveDifferentSizes(matrix1, matrix2)) {
            throw new IllegalArgumentException(String.format("Sizes of matrices for which difference to be calculated must coincide: " +
                    "matrix1 size = %s, matrix2 size = %s", matrix1.sizeToString(), matrix2.sizeToString()));
        }

        Matrix result = new Matrix(matrix1);
        result.subtract(matrix2);

        return result;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException(String.format("The number of columns in the first matrix must coincide " +
                    "with the number of rows in the second matrix: matrix1 size = %s, matrix2 size = %s",
                    matrix1.sizeToString(), matrix2.sizeToString()));
        }

        int rowsCount = matrix1.getRowsCount();
        int columnsCount = matrix2.getColumnsCount();
        Matrix result = new Matrix(rowsCount, columnsCount);

        for (int i = 0; i < rowsCount; i++) {
            Vector currentRow = matrix1.getRowAt(i);

            for (int j = 0; j < columnsCount; j++) {
                Vector currentColumn = matrix2.getColumnAt(j);
                result.matrixRows[i].setCoordinate(j, Vector.getScalarProduct(currentRow, currentColumn));
            }
        }

        return result;
    }

    private static boolean haveDifferentSizes(Matrix matrix1, Matrix matrix2) {
        return matrix1.getRowsCount() != matrix2.getRowsCount() ||
                matrix1.getColumnsCount() != matrix2.getColumnsCount();
    }
}