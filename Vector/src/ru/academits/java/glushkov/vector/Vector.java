package ru.academits.java.glushkov.vector;

import java.util.Arrays;

public class Vector {
    private double[] array;

    public Vector(int vectorSize) {
        if (vectorSize <= 0) {
            throw new IllegalArgumentException("Vector's size must be positive number: argument vectorSize = " + vectorSize);
        }

        array = new double[vectorSize];
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Constructor's argument must be not null");
        }

        int size = vector.array.length;
        array = new double[size];

        System.arraycopy(vector.array, 0, array, 0, size);
    }

    public Vector(double[] array) {
        if (array == null) {
            throw new NullPointerException("Constructor's array argument must be not null");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("Constructor's array argument length must be nonzero");
        }

        this.array = new double[array.length];

        System.arraycopy(array, 0, this.array, 0, array.length);
    }

    public Vector(int vectorSize, double[] array) {
        if (vectorSize <= 0) {
            throw new IllegalArgumentException("Vector's size must be positive number: argument vectorSize = " + vectorSize);
        }

        if (array == null) {
            throw new NullPointerException("Constructor's array argument must be not null");
        }

        this.array = new double[vectorSize];

        System.arraycopy(array, 0, this.array, 0, Math.min(array.length, vectorSize));
    }

    public int getSize() {
        return array.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(array).replace("[", "{").replace("]", "}");
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

        return Arrays.equals(v.array, array);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Arrays.hashCode(array);

        return hash;
    }

    public void add(Vector vector) {
        if (array.length < vector.array.length) {
            array = Arrays.copyOf(array, vector.array.length);
        }

        for (int i = 0; i < vector.array.length; i++) {
            array[i] += vector.array[i];
        }
    }

    public void subtract(Vector vector) {
        if (array.length < vector.array.length) {
            array = Arrays.copyOf(array, vector.array.length);
        }

        for (int i = 0; i < vector.array.length; i++) {
            array[i] -= vector.array[i];
        }
    }

    public void multiplyByScalar(double number) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= number;
        }
    }

    public void reverse() {
        multiplyByScalar(-1);
    }

    public double getLength() {
        double elementsSquaresSum = 0;

        for (double e : array) {
            elementsSquaresSum += e * e;
        }

        return Math.sqrt(elementsSquaresSum);
    }

    public double getCoordinate(int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Index must be non-negative and must be less than vector's size: index = " + index);
        }

        return array[index];
    }

    public void setCoordinate(int index, double number) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Index must be non-negative and must be less than vector's size: index = " + index);
        }

        array[index] = number;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector result = new Vector(Math.max(vector1.array.length, vector2.array.length), vector1.array);

        result.add(vector2);

        return result;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector result = new Vector(Math.max(vector1.array.length, vector2.array.length), vector1.array);

        result.subtract(vector2);

        return result;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0;

        int minVectorSize = Math.min(vector1.array.length, vector2.array.length);

        for (int i = 0; i < minVectorSize; i++) {
            scalarProduct += vector1.array[i] * vector2.array[i];
        }

        return scalarProduct;
    }
}