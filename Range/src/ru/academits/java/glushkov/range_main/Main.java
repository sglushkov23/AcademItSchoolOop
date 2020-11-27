package ru.academits.java.glushkov.range_main;

import ru.academits.java.glushkov.range.Range;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(5, 10);

        System.out.println("Значения полей после вызова конструктора:");
        System.out.printf("from = %.3f, to = %.3f%n%n", range1.getFrom(), range1.getTo());

        range1.setFrom(7);
        range1.setTo(16);
        System.out.println("Значения полей после их изменения с помощью сеттеров:");
        System.out.printf("from = %.3f, to = %.3f%n%n", range1.getFrom(), range1.getTo());

        System.out.println("Длина диапазона составляет " + range1.getLength());
        System.out.println();

        double number1 = 9;
        System.out.printf("Число %.3f %s диапазону %s%n%n", number1,
                range1.isInside(number1) ? "принадлежит" : "не принадлежит", range1);

        double number2 = 17;
        System.out.printf("Число %.3f %s диапазону %s%n%n", number2,
                range1.isInside(number2) ? "принадлежит" : "не принадлежит", range1);

        double number3 = 16;
        System.out.printf("Число %.3f %s диапазону %s%n%n", number3,
                range1.isInside(number3) ? "принадлежит" : "не принадлежит", range1);

        Range range2 = new Range(2, 5);
        Range range3 = new Range(5.1, 7);
        Range range4 = new Range(-1, 2);
        Range range5 = new Range(3, 7);
        Range range6 = new Range(3, 4);
        Range range7 = new Range(1, 4);
        Range range8 = new Range(0, 7);

        System.out.println("1) Пересечение:");
        System.out.println("Пересечение диапазонов " + range2 + " и " + range2 + ": " + range2.getIntersection(range2));
        System.out.println("Пересечение диапазонов " + range2 + " и " + range3 + ": " + range2.getIntersection(range3));
        System.out.println("Пересечение диапазонов " + range2 + " и " + range4 + ": " + range2.getIntersection(range4));
        System.out.println("Пересечение диапазонов " + range2 + " и " + range5 + ": " + range2.getIntersection(range5));
        System.out.println("Пересечение диапазонов " + range2 + " и " + range6 + ": " + range2.getIntersection(range6));
        System.out.println("Пересечение диапазонов " + range2 + " и " + range7 + ": " + range2.getIntersection(range7));
        System.out.println("Пересечение диапазонов " + range2 + " и " + range8 + ": " + range2.getIntersection(range8));

        System.out.println();
        System.out.println("2) Объединение:");
        System.out.println("Объединение диапазонов " + range2 + " и " + range2 + ": " + Arrays.toString(range2.getUnion(range2)));
        System.out.println("Объединение диапазонов " + range2 + " и " + range3 + ": " + Arrays.toString(range2.getUnion(range3)));
        System.out.println("Объединение диапазонов " + range2 + " и " + range4 + ": " + Arrays.toString(range2.getUnion(range4)));
        System.out.println("Объединение диапазонов " + range2 + " и " + range5 + ": " + Arrays.toString(range2.getUnion(range5)));
        System.out.println("Объединение диапазонов " + range2 + " и " + range6 + ": " + Arrays.toString(range2.getUnion(range6)));
        System.out.println("Объединение диапазонов " + range2 + " и " + range7 + ": " + Arrays.toString(range2.getUnion(range7)));
        System.out.println("Объединение диапазонов " + range2 + " и " + range8 + ": " + Arrays.toString(range2.getUnion(range8)));

        System.out.println();
        System.out.println("3) Разность:");
        System.out.println("Разность диапазонов " + range2 + " и " + range2 + ": " + Arrays.toString(range2.getDifference(range2)));
        System.out.println("Разность диапазонов " + range2 + " и " + range3 + ": " + Arrays.toString(range2.getDifference(range3)));
        System.out.println("Разность диапазонов " + range2 + " и " + range4 + ": " + Arrays.toString(range2.getDifference(range4)));
        System.out.println("Разность диапазонов " + range2 + " и " + range5 + ": " + Arrays.toString(range2.getDifference(range5)));
        System.out.println("Разность диапазонов " + range2 + " и " + range6 + ": " + Arrays.toString(range2.getDifference(range6)));
        System.out.println("Разность диапазонов " + range2 + " и " + range7 + ": " + Arrays.toString(range2.getDifference(range7)));
        System.out.println("Разность диапазонов " + range2 + " и " + range8 + ": " + Arrays.toString(range2.getDifference(range8)));
    }
}
