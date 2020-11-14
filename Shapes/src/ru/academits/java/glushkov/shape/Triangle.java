package ru.academits.java.glushkov.shape;

public class Triangle implements Shape {
    private final double x1;
    private final double x2;
    private final double x3;
    private final double y1;
    private final double y2;
    private final double y3;
    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(double x1, double x2, double x3,
                    double y1, double y2, double y3) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;

        sideA = calculateDistance(x1, y1, x2, y2);
        sideB = calculateDistance(x1, y1, x3, y3);
        sideC = calculateDistance(x2, y2, x3, y3);
    }

    @Override
    public double getWidth() {
        double[] array = new double[]{x1, x2, x3};

        return getMax(array) - getMin(array);
    }

    @Override
    public double getHeight() {
        double[] array = new double[]{y1, y2, y3};

        return getMax(array) - getMin(array);
    }

    @Override
    public double getArea() {
        double semiPerimeter = getPerimeter() / 2;

        return Math.sqrt(semiPerimeter * (semiPerimeter - sideA) *
                (semiPerimeter - sideB) * (semiPerimeter - sideC));
    }

    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public String toString() {
        return String.format("{ Shape type: Triangle%n  x1 = %.2f%n  x2 = %.2f%n  x3 = %.2f%n  " +
                "y1 = %.2f%n  y2 = %.2f%n  y3 = %.2f }", x1, x2, x3, y1, y2, y3);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Double.hashCode(x1);
        hash = prime * hash + Double.hashCode(x2);
        hash = prime * hash + Double.hashCode(x3);
        hash = prime * hash + Double.hashCode(y1);
        hash = prime * hash + Double.hashCode(y2);
        hash = prime * hash + Double.hashCode(y3);

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Triangle t = (Triangle) o;
        double epsilon = 1e-10;

        return Math.abs(t.x1 - x1) < epsilon && Math.abs(t.x2 - x2) < epsilon &&
                Math.abs(t.x3 - x3) < epsilon && Math.abs(t.y1 - y1) < epsilon &&
                Math.abs(t.y2 - y2) < epsilon && Math.abs(t.y3 - y3) < epsilon;
    }

    private static double getMin(double[] array) {
        double min = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    private static double getMax(double[] array) {
        double max = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    private static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
