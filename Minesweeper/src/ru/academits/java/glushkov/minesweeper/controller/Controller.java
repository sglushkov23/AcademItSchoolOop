package ru.academits.java.glushkov.minesweeper.controller;

import ru.academits.java.glushkov.minesweeper.gui.View;

public interface Controller {
    void setView(View view);

    void updateOnLeftClick(int clickedCellNumber);

    void updateOnRightClick(int clickedCellNumber);

    void setGameParameters(int rowsCount, int columnsCount, int minedCellsCount);

    String[][] getRecordData();
}