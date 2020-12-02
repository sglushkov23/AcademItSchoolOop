package ru.academits.java.glushkov.vector;

import java.util.Arrays;

public class Vector {
    private double[] coordinates;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Vector's size must be positive number: argument vectorSize = " + size);
        }

        coordinates = new double[size];
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Constructor's argument must be not null");
        }

        coordinates = Arrays.copyOf(vector.coordinates, vector.coordinates.length);
    }

    public Vector(double[] coordinates) {
        if (coordinates == null) {
            throw new NullPointerException("Constructor's array argument must be not null");
        }

        if (coordinates.length == 0) {
            throw new IllegalArgumentException("Constructor's array argument length must be nonzero");
        }

        this.coordinates = Arrays.copyOf(coordinates, coordinates.length);
    }

    public Vector(int size, double[] coordinates) {
        if (size <= 0) {
            throw new IllegalArgumentException("Vector's size must be positive number: argument vectorSize = " + size);
        }

        if (coordinates == null) {
            throw new NullPointerException("Constructor's array argument must be not null");
        }

        this.coordinates = Arrays.copyOf(coordinates, size);
    }

    public int getSize() {
        return coordinates.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(coordinates).replace("[", "{").replace("]", "}");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Vector v = (Vector) o;

        return Arrays.equals(v.coordinates, coordinates);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Arrays.hashCode(coordinates);

        return hash;
    }

    public void add(Vector vector) {
        if (coordinates.length < vector.coordinates.length) {
            coordinates = Arrays.copyOf(coordinates, vector.coordinates.length);
        }

        for (int i = 0; i < vector.coordinates.length; i++) {
            coordinates[i] += vector.coordinates[i];
        }
    }

    public void subtract(Vector vector) {
        if (coordinates.length < vector.coordinates.length) {
            coordinates = Arrays.copyOf(coordinates, vector.coordinates.length);
        }

        for (int i = 0; i < vector.coordinates.length; i++) {
            coordinates[i] -= vector.coordinates[i];
        }
    }

    public void multiplyByScalar(double number) {
        for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] *= number;
        }
    }

    public void reverse() {
        multiplyByScalar(-1);
    }

    public double getLength() {
        double elementsSquaresSum = 0;

        for (double e : coordinates) {
            elementsSquaresSum += e * e;
        }

        return Math.sqrt(elementsSquaresSum);
    }

    public double getCoordinate(int index) {
        if (index < 0 || index >= coordinates.length) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index must be non-negative and must be less than vector's size (%d): index = %d",
                    coordinates.length, index));
        }

        return coordinates[index];
    }

    public void setCoordinate(int index, double number) {
        if (index < 0 || index >= coordinates.length) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index must be non-negative and must be less than vector's size (%d): index = %d",
                    coordinates.length, index));
        }

        coordinates[index] = number;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector result = new Vector(Math.max(vector1.coordinates.length, vector2.coordinates.length), vector1.coordinates);

        result.add(vector2);

        return result;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector result = new Vector(Math.max(vector1.coordinates.length, vector2.coordinates.length), vector1.coordinates);

        result.subtract(vector2);

        return result;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0;

        int minVectorSize = Math.min(vector1.coordinates.length, vector2.coordinates.length);

        for (int i = 0; i < minVectorSize; i++) {
            scalarProduct += vector1.coordinates[i] * vector2.coordinates[i];
        }

        return scalarProduct;
    }
}