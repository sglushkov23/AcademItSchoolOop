package ru.academits.java.glushkov.shape;

import java.util.Comparator;

public class PerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape o1, Shape o2) {
        if (o1.getPerimeter() - o2.getPerimeter() > 0) {
            return 1;
        }

        if (o1.getPerimeter() - o2.getPerimeter() < 0) {
            return -1;
        }

        return 0;
    }
}
