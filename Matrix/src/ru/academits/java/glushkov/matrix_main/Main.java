package ru.academits.java.glushkov.matrix_main;

import ru.academits.java.glushkov.matrix.Matrix;
import ru.academits.java.glushkov.vector.Vector;

public class Main {
    public static void main(String[] args) {
        System.out.println("1) Тестирование конструкторов");
        System.out.println("a. Конструктор Matrix(n, m):");

        int rowsCount = 3;
        int columnsCount = 4;
        Matrix matrix1 = new Matrix(rowsCount, columnsCount);

        System.out.printf("Вызов конструктора Matrix(n, m) с параметрами n = %d; m = %d%n", rowsCount, columnsCount);
        System.out.println("matrix1 = " + matrix1);

        System.out.println();
        System.out.println("b. Конструктор Matrix(Matrix):");

        Matrix matrix2 = new Matrix(matrix1);

        System.out.println("Вызов конструктора Matrix(Matrix) с параметром matrix1");
        System.out.println("matrix2 = " + matrix2);

        System.out.println();
        System.out.println("c. Конструктор Matrix(double[][]):");

        double[][] array = {
                {1, 2, 3, 4, 5},
                {6, 7},
                {8},
                {9, 10, 11, 12}
        };
        Matrix matrix3 = new Matrix(array);

        System.out.println("matrix3 = " + matrix3);

        System.out.println();
        System.out.println("d. Конструктор Matrix(Vector[]):");

        Vector v1 = new Vector(new double[]{1, 2});
        Vector v2 = new Vector(new double[]{3, 4, 5, 6});
        Vector v3 = new Vector(new double[]{7});

        Vector[] vectorsArray = new Vector[]{v1, v2, v3};
        Matrix matrix4 = new Matrix(vectorsArray);

        System.out.println("matrix4 = " + matrix4);

        System.out.println("2) Тестирование нестатических методов");
        System.out.println("a. Получение размеров матрицы:");

        Matrix matrix5 = new Matrix(3, 5);

        System.out.println("matrix5 = " + matrix5);
        System.out.println("matrix5 rows count: " + matrix5.getRowsCount());
        System.out.println("matrix5 columns count: " + matrix5.getColumnsCount());

        System.out.println();
        System.out.println("b. Получение и задание вектора-строки по индексу:");

        System.out.println("matrix3 = " + matrix3);
        System.out.println("Строка с индексом 1 матрицы matrix3: " + matrix3.getRowAt(1));
        System.out.println("Строка с индексом 3 матрицы matrix3: " + matrix3.getRowAt(3));

        matrix3.setRowAt(2, new Vector(new double[]{11, 13, 17, 23, 99}));

        System.out.println();
        System.out.println("После задания вектор-строки [11, 13, 17, 23, 99] по индексу 2:");
        System.out.println("matrix3 = " + matrix3);

        System.out.println();
        System.out.println("c. Получение вектора-столбца по индексу:");

        System.out.println("Столбец с индексом 0 матрицы matrix3: " + matrix3.getColumnAt(0));
        System.out.println("Столбец с индексом 3 матрицы matrix3: " + matrix3.getColumnAt(3));

        System.out.println();
        System.out.println("d. Транспонирование матрицы:");

        System.out.println("matrix3 = " + matrix3);
        System.out.printf("matrix3 size = [%d, %d]%n", matrix3.getRowsCount(), matrix3.getColumnsCount());

        matrix3.transpose();

        System.out.println("После транспонироавания:");
        System.out.println("matrix3 = " + matrix3);
        System.out.printf("matrix3 size = [%d, %d]%n", matrix3.getRowsCount(), matrix3.getColumnsCount());

        System.out.println();
        System.out.println("e. Умножение на скаляр:");

        System.out.println("matrix3 = " + matrix3);

        matrix3.multiplyBy(2);

        System.out.println("После умножения на скаляр 2:");
        System.out.println("matrix3 = " + matrix3);

        System.out.println();
        System.out.println("f. Вычисление определителя матрицы:");

        Matrix matrixDet1 = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {4, -5, 6, 7},
                {7, 8, -9, 10},
                {-10, 11, 12, 13}
        });
        System.out.println("matrixDet1 = " + matrixDet1);
        System.out.println("Определитель матрицы matrixDet1 = " + matrixDet1.getDeterminant());
        System.out.println();

        Matrix matrixDet2 = new Matrix(new double[][]{
                {1, -2, 3, 4},
                {4, -5, 6, 7},
                {7, 8, -9, 10},
                {-10, 11, 12, 13}
        });
        System.out.println("matrixDet2 = " + matrixDet2);
        System.out.println("Определитель матрицы matrixDet2 = " + matrixDet2.getDeterminant());
        System.out.println();

        Matrix matrixDet3 = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {4, 5, 6, 7},
                {7, 8, 9, 10},
                {10, 11, 12, 13}
        });
        System.out.println("matrixDet3 = " + matrixDet3);
        System.out.println("Определитель матрицы matrixDet3 = " + matrixDet3.getDeterminant());
        System.out.println();

        Matrix matrixDet4 = new Matrix(new double[][]{{-7}});
        System.out.println("matrixDet4 = " + matrixDet4);
        System.out.println("Определитель матрицы matrixDet4 = " + matrixDet4.getDeterminant());
        System.out.println();

        System.out.println("h. Умножение матрицы на вектор:");

        Matrix matrix6 = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        });
        Vector vector1 = new Vector(new double[]{1, 2, 3, 4});

        System.out.println("matrix6 = " + matrix6);
        System.out.printf("Размер матрицы matrix6 = [%d, %d]%n", matrix6.getRowsCount(), matrix6.getColumnsCount());
        System.out.println();
        System.out.println("vector1 = " + vector1);
        System.out.println("Размер вектора vector1 = " + vector1.getSize());

        Vector result = matrix6.multiplyBy(vector1);

        System.out.println();
        System.out.println("Результат умножения матрицы matrix6 на вектор vector1: " + result);
        System.out.println("Размер вектора result = " + result.getSize());

        System.out.println();
        System.out.println("i. Сложение матриц:");

        Matrix matrix7 = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        });
        Matrix matrix8 = new Matrix(new double[][]{
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        });

        System.out.println("matrix7 = " + matrix7);
        System.out.println("matrix8 = " + matrix8);

        matrix7.add(matrix8);

        System.out.println("После сложения:");
        System.out.println("matrix7 = " + matrix7);
        System.out.println("matrix8 = " + matrix8);

        System.out.println();
        System.out.println("j. Вычитание матриц:");

        Matrix matrix9 = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        });
        Matrix matrix10 = new Matrix(new double[][]{
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11}
        });

        System.out.println("matrix9 = " + matrix9);
        System.out.println("matrix10 = " + matrix10);

        matrix9.subtract(matrix10);

        System.out.println("После вычитания:");
        System.out.println("matrix9 = " + matrix9);
        System.out.println("matrix10 = " + matrix10);

        System.out.println();
        System.out.println("3) Тестирование статических методов");
        System.out.println("a. Сложение матриц:");

        Matrix matrix11 = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        });
        Matrix matrix12 = new Matrix(new double[][]{
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        });

        System.out.println("matrix11 = " + matrix11);
        System.out.println("matrix12 = " + matrix12);

        Matrix matrix13 = Matrix.getSum(matrix11, matrix12);

        System.out.println("После сложения:");
        System.out.println("matrix11 = " + matrix11);
        System.out.println("matrix12 = " + matrix12);
        System.out.println("Сумма матриц matrix11 и matrix12 = " + matrix13);

        System.out.println();
        System.out.println("b. Вычитание матриц:");
        Matrix matrix14 = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        });
        Matrix matrix15 = new Matrix(new double[][]{
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        });

        System.out.println("matrix14 = " + matrix14);
        System.out.println("matrix15 = " + matrix15);

        Matrix matrix16 = Matrix.getDifference(matrix14, matrix15);

        System.out.println("После вычитания:");
        System.out.println("matrix14 = " + matrix14);
        System.out.println("matrix15 = " + matrix15);
        System.out.println("Разность матриц matrix14 и matrix15 = " + matrix16);

        System.out.println();
        System.out.println("c. Умножение матриц:");

        Matrix matrix17 = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        });
        Matrix matrix18 = new Matrix(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        });

        System.out.println("matrix17 = " + matrix17);
        System.out.printf("Размер матрицы matrix17 = [%d, %d]%n", matrix17.getRowsCount(), matrix17.getColumnsCount());
        System.out.println("matrix18 = " + matrix18);
        System.out.printf("Размер матрицы matrix18 = [%d, %d]%n", matrix18.getRowsCount(), matrix18.getColumnsCount());

        Matrix matrix19 = Matrix.getProduct(matrix17, matrix18);

        System.out.println("Произведение матриц matrix17 и matrix18 (matrix19) = " + matrix19);
        System.out.printf("Размер матрицы matrix19 = [%d, %d]%n", matrix19.getRowsCount(), matrix19.getColumnsCount());
    }
}