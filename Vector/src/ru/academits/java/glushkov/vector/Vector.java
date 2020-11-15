package ru.academits.java.glushkov.vector;

import java.util.Arrays;

public class Vector {
    private double[] vector;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Vector's size must be positive number");
        }

        vector = new double[n];
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Constructor argument must be not null");
        }

        int size = vector.getSize();
        this.vector = new double[size];

        for (int i = 0; i < size; i++) {
            this.vector[i] = vector.getAt(i);
        }
    }

    public Vector(double[] array) {
        if (array == null) {
            throw new NullPointerException("Constructor argument must be not null");
        }

        int size = array.length;
        vector = new double[size];

        System.arraycopy(array, 0, vector, 0, size);
    }

    public Vector(int n, double[] array) {
        if (array == null) {
            throw new NullPointerException("Constructor argument must be not null");
        }

        int size = Math.max(n, array.length);
        vector = new double[size];

        System.arraycopy(array, 0, vector, 0, array.length);
    }

    public int getSize() {
        return vector.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(vector);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Vector v = (Vector) o;

        if (v.getSize() != vector.length) {
            return false;
        }

        for (int i = 0; i < vector.length; i++) {
            if (v.getAt(i) != vector[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Arrays.hashCode(vector);

        return hash;
    }

    public void add(Vector vector) {
        int size1 = this.vector.length;
        int size2 = vector.getSize();

        if (size1 >= size2) {
            for (int i = 0; i < size2; i++) {
                this.vector[i] += vector.getAt(i);
            }
        } else {
            Vector copy = new Vector(size2, this.vector);
            this.vector = new double[size2];

            for (int i = 0; i < size2; i++) {
                this.vector[i] = copy.getAt(i) + vector.getAt(i);
            }
        }
    }

    public void subtract(Vector vector) {
        int size1 = this.vector.length;
        int size2 = vector.getSize();

        if (size1 >= size2) {
            for (int i = 0; i < size2; i++) {
                this.vector[i] -= vector.getAt(i);
            }
        } else {
            Vector copy = new Vector(size2, this.vector);
            this.vector = new double[size2];

            for (int i = 0; i < size2; i++) {
                this.vector[i] = copy.getAt(i) - vector.getAt(i);
            }
        }
    }

    public void multiplyBy(double number) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] *= number;
        }
    }

    public void reverse() {
        multiplyBy(-1);
    }

    public double getLength() {
        return Math.sqrt(getScalarProduct(this, this));
    }

    public double getAt(int index) {
        if (index > vector.length - 1) {
            throw new RuntimeException("Index must not exceed vector's size minus 1");
        }

        return vector[index];
    }

    public void setAt(int index, double number) {
        if (index > vector.length - 1) {
            throw new RuntimeException("Index must not exceed vector's size minus 1");
        }

        vector[index] = number;
    }

    public static Vector getSum(Vector v1, Vector v2) {
        int size1 = v1.getSize();
        int size2 = v2.getSize();
        Vector sum;

        if (size1 >= size2) {
            sum = new Vector(v1);
        } else {
            sum = new Vector(v2);
        }

        for (int i = 0; i < Math.min(size1, size2); i++) {
            sum.setAt(i, v1.getAt(i) + v2.getAt(i));
        }

        return sum;
    }

    public static Vector getDifference(Vector v1, Vector v2) {
        int size1 = v1.getSize();
        int size2 = v2.getSize();
        Vector difference;

        if (size1 >= size2) {
            difference = new Vector(v1);
        } else {
            difference = new Vector(v2);
        }

        for (int i = 0; i < Math.min(size1, size2); i++) {
            difference.setAt(i, v1.getAt(i) - v2.getAt(i));
        }

        if (size2 > size1) {
            for (int i = size1; i < size2; i++) {
                difference.setAt(i, -v2.getAt(i));
            }
        }

        return difference;
    }

    public static double getScalarProduct(Vector v1, Vector v2) {
        int size1 = v1.getSize();
        int size2 = v2.getSize();
        double scalarProduct = 0;

        for (int i = 0; i < Math.min(size1, size2); i++) {
            scalarProduct += v1.getAt(i) * v2.getAt(i);
        }

        return scalarProduct;
    }
}
