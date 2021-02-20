package ru.academits.java.glushkov.minesweeper.gui;

public interface View {
    void start();

    void updateCell(int cellNumber, String data, int activeMinesCount);

    void showMessage(String message);

    void showMinedCell(int cellNumber, boolean isWin);

    void disableField();

    String getTime();
}
