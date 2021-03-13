package ru.academits.java.glushkov.minesweeper.model;

import java.util.ArrayList;
import java.util.TreeSet;

public interface Game {
    void setParameters(int rowsCount, int columnsCount, int minedCellsCount);

    void setTime(String duration);

    String[][] getRecordData();

    void selectCell(int cellNumber);

    void markCell(int cellNumber);

    boolean isOver();

    boolean isWin();

    String getMessage();

    int getActiveMinesCount();

    int[] getMinedNeighborsCountsForCells();

    ArrayList<Integer> getUpdatedCellsNumbers();

    TreeSet<Integer> getMinedCellsSet();

    CellStatus[] getCellsStatusMap();
}