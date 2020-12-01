package ru.academits.java.glushkov.shape;

public class Triangle implements Shape {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double x3;
    private double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public double getY3() {
        return y3;
    }

    public void setY3(double y3) {
        this.y3 = y3;
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
        double side1Length = getSideLength(x1, y1, x2, y2);
        double side2Length = getSideLength(x1, y1, x3, y3);
        double side3Length = getSideLength(x2, y2, x3, y3);

        double semiPerimeter = (side1Length + side2Length + side3Length) / 2;

        return Math.sqrt(semiPerimeter * (semiPerimeter - side1Length) *
                (semiPerimeter - side2Length) * (semiPerimeter - side3Length));
    }

    @Override
    public double getPerimeter() {
        double side1Length = getSideLength(x1, y1, x2, y2);
        double side2Length = getSideLength(x1, y1, x3, y3);
        double side3Length = getSideLength(x2, y2, x3, y3);

        return side1Length + side2Length + side3Length;
    }

    @Override
    public String toString() {
        return String.format("{Triangle: (%.2f; %.2f), (%.2f; %.2f), (%.2f; %.2f)}", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Double.hashCode(x1);
        hash = prime * hash + Double.hashCode(y1);
        hash = prime * hash + Double.hashCode(x2);
        hash = prime * hash + Double.hashCode(y2);
        hash = prime * hash + Double.hashCode(x3);
        hash = prime * hash + Double.hashCode(y3);

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Triangle t = (Triangle) o;

        return t.x1 == x1 && t.x2 == x2 && t.x3 == x3 && t.y1 == y1 && t.y2 == y2 && t.y3 == y3;
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

    private static double getSideLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}