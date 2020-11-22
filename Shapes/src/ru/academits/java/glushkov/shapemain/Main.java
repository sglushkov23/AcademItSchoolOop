package ru.academits.java.glushkov.shapemain;

import ru.academits.java.glushkov.shape.*;

import java.util.Arrays;

public class Main {
    public static Shape getShapeByArea(Shape[] array, int index, boolean isDescending) {
        if (index < 0 || index >= array.length) {
            return null;
        }

        Arrays.sort(array, new AreaComparator());

        return isDescending ? array[array.length - index - 1] : array[index];
    }

    public static Shape getShapeByPerimeter(Shape[] array, int index, boolean isDescending) {
        if (index < 0 || index >= array.length) {
            return null;
        }

        Arrays.sort(array, new PerimeterComparator());

        return isDescending ? array[array.length - index - 1] : array[index];
    }

    public static void main(String[] args) {
        Shape[] shapesArray = {
                new Triangle(0, 0, 4, 0, 0, 2),
                new Circle(2),
                new Square(2),
                new Triangle(4, 0, 7, 3, 5, 5),
                new Rectangle(2, 6),
                new Circle(1),
                new Square(3),
                new Rectangle(3, 6),
                new Square(0.5),
                new Rectangle(3, 4)
        };

        System.out.println("Фигура с максимальной площадью:");
        System.out.println(getShapeByArea(shapesArray, 0, true));

        System.out.println();
        System.out.println("Фигура со вторым по величине периметром:");
        System.out.println(getShapeByPerimeter(shapesArray, 1, true));
    }
}