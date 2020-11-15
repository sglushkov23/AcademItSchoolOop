package ru.academits.java.glushkov.vector;

public class Main {
    public static void main(String[] args) {
        System.out.println("1) Тестирование конструкторов:");
        System.out.println("a:");

        try {
            Vector v1 = new Vector(5);
            System.out.println("v1 = " + v1);

            Vector v2 = new Vector(0);
            System.out.println("v2 = " + v2);
        } catch (IllegalArgumentException e) {
            System.out.println("Error occurred: " + e.getMessage());
            //e.printStackTrace();
        }

        System.out.println();
        System.out.println("b and c:");

        Vector v1 = new Vector(new double[]{1, 2, 3});
        Vector v2 = new Vector(v1);
        System.out.println("v1 = " + v1);
        System.out.println("v2 = " + v2);

        System.out.println();
        System.out.println("d:");

        Vector v3 = new Vector(7, new double[]{4, 5, 6});
        System.out.println("v3 = " + v3);

        System.out.println();
        System.out.println("2) Метод getSize():");

        Vector v4 = new Vector(7, new double[]{4, 5, 6});
        System.out.println("v4 = " + v4);
        System.out.println("Размер вектора v4 равен " + v4.getSize());

        System.out.println();
        System.out.println("4) Тестирование нестатических методов:");
        System.out.println("a. Прибавление к вектору другого вектора");

        Vector v5 = new Vector(new double[]{4, 5, 6});
        Vector v6 = new Vector(new double[]{1, 2});
        Vector v7 = new Vector(new double[]{1, 2, 3, 4, 5});

        System.out.println("Исходные векторы:");
        System.out.println("v5 = " + v5);
        System.out.println("v6 = " + v6);
        System.out.println("v7 = " + v7);

        v5.add(v6);

        System.out.println("После прибавления к вектору v5 вектора v6:");
        System.out.println("v5 = " + v5);
        System.out.println("v6 = " + v6);
        System.out.println("v7 = " + v7);

        v5.add(v7);

        System.out.println("После прибавления к вектору v5 вектора v7:");
        System.out.println("v5 = " + v5);
        System.out.println("v6 = " + v6);
        System.out.println("v7 = " + v7);

        System.out.println();
        System.out.println("b. Вычитание из вектора другого вектора");

        Vector v8 = new Vector(new double[]{4, 5, 6});
        Vector v9 = new Vector(new double[]{1, 2});
        Vector v10 = new Vector(new double[]{1, 2, 3, 4, 5});

        System.out.println("Исходные векторы:");
        System.out.println("v8 = " + v8);
        System.out.println("v9 = " + v9);
        System.out.println("v10 = " + v10);

        v8.subtract(v9);

        System.out.println("После вычитания из вектора v8 вектора v9:");
        System.out.println("v8 = " + v8);
        System.out.println("v9 = " + v9);
        System.out.println("v10 = " + v10);

        v8.subtract(v10);

        System.out.println("После вычитания из вектора v8 вектора v10:");
        System.out.println("v8 = " + v8);
        System.out.println("v9 = " + v9);
        System.out.println("v10 = " + v10);

        System.out.println();
        System.out.println("c. Умножение вектора на скаляр");

        Vector v11 = new Vector(new double[]{1, 2, 3, 4, 5});

        System.out.println("Исходный вектор:");
        System.out.println("v11 = " + v11);

        v11.multiplyBy(2);

        System.out.println("После умножения вектора v11 на скаляр 2:");
        System.out.println("v11 = " + v11);

        System.out.println();
        System.out.println("d. Разворот вектора");

        Vector v12 = new Vector(new double[]{5, 6, 7, 8, 9});

        System.out.println("Исходный вектор:");
        System.out.println("v12 = " + v12);

        v12.reverse();

        System.out.println("После разворота вектора v12:");
        System.out.println("v12 = " + v12);

        System.out.println();
        System.out.println("e. Получение длины вектора");

        Vector v13 = new Vector(new double[]{3, 4});

        System.out.println("Длина вектора v13 равна " + v13.getLength());

        System.out.println();
        System.out.println("f. Получение и установка компоненты вектора по индексу");

        Vector v14 = new Vector(new double[]{3, 7, 14, 19});

        System.out.println("Исходный вектор:");
        System.out.println("v14 = " + v14);

        System.out.println("Значение компоненты вектора v14 по индексу 2 = " + v14.getAt(2));

        v14.setAt(1, 37);

        System.out.println("После установки значения 37 по индексу 1:");
        System.out.println("v14 = " + v14);

        System.out.println();
        System.out.println("g. Тестирование переопределенных методов equals и hashCode");

        Vector v15 = new Vector(new double[]{2, 5, 11, 13});
        Vector v16 = new Vector(new double[]{2, 5, 11, 13, 4});
        Vector v17 = new Vector(new double[]{2, 5, 11, 13});

        System.out.println("Проверка на равенство вектора v15 с самим собой: " + v15.equals(v15));
        System.out.println("Проверка на равенство вектора v16 с самим собой: " + v16.equals(v16));
        System.out.println("Проверка на равенство вектора v17 с самим собой: " + v17.equals(v17));
        System.out.println("Проверка на равенство векторов v15 и v16: " + v15.equals(v16));
        System.out.println("Проверка на равенство векторов v16 и v15: " + v16.equals(v15));
        System.out.println("Проверка на равенство векторов v15 и v17: " + v15.equals(v17));
        System.out.println("Проверка на равенство векторов v17 и v15: " + v17.equals(v15));
        System.out.println("Проверка на равенство векторов v16 и v17: " + v16.equals(v17));
        System.out.println("Проверка на равенство векторов v17 и v16: " + v17.equals(v16));

        System.out.println();
        System.out.println("Хэш-код для вектора v15:" + v15.hashCode());
        System.out.println("Хэш-код для вектора v16:" + v16.hashCode());
        System.out.println("Хэш-код для вектора v17:" + v17.hashCode());

        System.out.println();
        System.out.println("5) Тестирование статических методов:");
        System.out.println("a. Сложение двух векторов");

        Vector v18 = new Vector(new double[]{1, 3, 5, 7});
        Vector v19 = new Vector(new double[]{9, 6, 4, 1, 3, 5, 8});
        Vector v20 = new Vector(new double[]{3, 7});

        System.out.println("Исходные векторы:");
        System.out.println("v18 = " + v18);
        System.out.println("v19 = " + v19);
        System.out.println("v20 = " + v20);

        System.out.println();
        System.out.println("Сумма векторов v18 и v19 = " + Vector.getSum(v18, v19));
        System.out.println("Сумма векторов v18 и v20 = " + Vector.getSum(v18, v20));

        System.out.println();
        System.out.println("После выполнения суммирования векторы v18, v19, v20 остались неизменными:");
        System.out.println("v18 = " + v18);
        System.out.println("v19 = " + v19);
        System.out.println("v20 = " + v20);

        System.out.println();
        System.out.println("b. Вычитание векторов");

        System.out.println("Исходные векторы:");
        System.out.println("v18 = " + v18);
        System.out.println("v19 = " + v19);
        System.out.println("v20 = " + v20);

        System.out.println();
        System.out.println("Разность векторов v18 и v18 = " + Vector.getDifference(v18, v18));
        System.out.println("Разность векторов v18 и v19 = " + Vector.getDifference(v18, v19));
        System.out.println("Разность векторов v19 и v18 = " + Vector.getDifference(v19, v18));
        System.out.println("Разность векторов v18 и v20 = " + Vector.getDifference(v18, v20));
        System.out.println("Разность векторов v20 и v18 = " + Vector.getDifference(v20, v18));

        System.out.println();
        System.out.println("После выполнения вычитания векторы v18, v19, v20 остались неизменными:");
        System.out.println("v18 = " + v18);
        System.out.println("v19 = " + v19);
        System.out.println("v20 = " + v20);

        System.out.println();
        System.out.println("с. Скалярное произведение векторов");

        System.out.println("Исходные векторы:");
        System.out.println("v18 = " + v18);
        System.out.println("v19 = " + v19);
        System.out.println("v20 = " + v20);

        System.out.println();
        System.out.println("Скалярное произведение векторов v18 и v18 = " + Vector.getScalarProduct(v18, v18));
        System.out.println("Скалярное произведение векторов v18 и v19 = " + Vector.getScalarProduct(v18, v19));
        System.out.println("Скалярное произведение векторов v19 и v18 = " + Vector.getScalarProduct(v19, v18));
        System.out.println("Скалярное произведение векторов v18 и v20 = " + Vector.getScalarProduct(v18, v20));
        System.out.println("Скалярное произведение векторов v20 и v18 = " + Vector.getScalarProduct(v20, v18));
    }
}
