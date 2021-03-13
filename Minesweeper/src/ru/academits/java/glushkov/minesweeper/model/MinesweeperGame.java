package ru.academits.java.glushkov.minesweeper.model;

import java.util.*;

public class MinesweeperGame implements Game {
    private int rowsCount;
    private int columnsCount;
    private int minedCellsCount;
    private TreeSet<Integer> minedCellsSet;
    private int[] minedNeighborsCountsForCells;
    private ArrayList<Integer>[] neighborsListsForCells;
    private boolean[] mined;
    private CellStatus[] cellsStatusMap;
    private boolean isInitialized = false;
    private ArrayList<Integer> updatedCellsNumbers;
    private int activeMinesCount;
    private int openedCellsCount;
    private boolean isOver;
    private boolean isWin;
    private String message;
    private String time;

    public MinesweeperGame() {
    }

    @Override
    public void setParameters(int rowsCount, int columnsCount, int minedCellsCount) {
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.minedCellsCount = minedCellsCount;
        activeMinesCount = minedCellsCount;

        cellsStatusMap = new CellStatus[this.rowsCount * this.columnsCount];
        Arrays.fill(cellsStatusMap, CellStatus.UNMARKED);

        isInitialized = false;
        isOver = false;
        updatedCellsNumbers = null;
    }

    @Override
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String[][] getRecordData() {
        return RecordsFileHandler.getRecordDataForEachLevel();
    }

    @Override
    public int getActiveMinesCount() {
        return activeMinesCount;
    }

    @Override
    public boolean isOver() {
        return isOver;
    }

    @Override
    public boolean isWin() {
        return isWin;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ArrayList<Integer> getUpdatedCellsNumbers() {
        return updatedCellsNumbers;
    }

    @Override
    public TreeSet<Integer> getMinedCellsSet() {
        return minedCellsSet;
    }

    @Override
    public int[] getMinedNeighborsCountsForCells() {
        return minedNeighborsCountsForCells;
    }

    @Override
    public void selectCell(int selectedCellNumber) {
        if (!isInitialized) {
            initializeGame(selectedCellNumber);
        }

        if (cellsStatusMap[selectedCellNumber].equals(CellStatus.UNMARKED)) {
            if (mined[selectedCellNumber]) {
                activeMinesCount--;
                terminateGame(false);

                return;
            }

            cellsStatusMap[selectedCellNumber] = CellStatus.OPENED;
            openedCellsCount++;
            updatedCellsNumbers = new ArrayList<>();
            updatedCellsNumbers.add(selectedCellNumber);

            if (minedNeighborsCountsForCells[selectedCellNumber] != 0) {
                if (openedCellsCount == rowsCount * columnsCount - minedCellsCount) {
                    terminateGame(true);
                }

                return;
            }

            openSurroundingCellsForZeroCell(selectedCellNumber);
        }

        if (openedCellsCount == rowsCount * columnsCount - minedCellsCount) {
            terminateGame(true);
        }
    }

    public CellStatus[] getCellsStatusMap() {
        return cellsStatusMap;
    }

    @Override
    public void markCell(int cellNumber) {
        if (cellsStatusMap[cellNumber].equals(CellStatus.OPENED)) {
            return;
        }

        if (cellsStatusMap[cellNumber].equals(CellStatus.UNMARKED)) {
            cellsStatusMap[cellNumber] = CellStatus.FLAGGED;
            activeMinesCount--;

            return;
        }

        if (cellsStatusMap[cellNumber].equals(CellStatus.FLAGGED)) {
            cellsStatusMap[cellNumber] = CellStatus.QUESTIONED;
            activeMinesCount++;

            return;
        }

        cellsStatusMap[cellNumber] = CellStatus.UNMARKED;
    }

    private void initializeGame(int firstSelectedCell) {
        generateMinedCellsNumbers(firstSelectedCell);
        activeMinesCount = minedCellsCount;
        openedCellsCount = 0;

        int cellsCount = rowsCount * columnsCount;
        minedNeighborsCountsForCells = new int[cellsCount];
        //noinspection unchecked
        neighborsListsForCells = (ArrayList<Integer>[]) new ArrayList<?>[cellsCount];

        for (int i = 0; i < cellsCount; i++) {
            ArrayList<Integer> neighborsList = new ArrayList<>();
            minedNeighborsCountsForCells[i] = getMinedNeighborsCountAndGenerateNeighborsList(i, neighborsList);
            neighborsListsForCells[i] = neighborsList;
        }

        isInitialized = true;
    }

    private void terminateGame(boolean isWin) {
        isOver = true;
        this.isWin = isWin;
        message = this.isWin ? "Congratulations!" : "You'll have better luck next time";
        RecordsFileHandler recordsFileHandler = new RecordsFileHandler(rowsCount, columnsCount, minedCellsCount, time);

        if (this.isWin) {
            recordsFileHandler.checkRecord();
            recordsFileHandler.writeResultToFile();

            if (recordsFileHandler.isRecord()) {
                message = String.format("%s%nNew record: %s%nPrevious record: %s", message, time, recordsFileHandler.getRecordTime());
            } else {
                message = String.format("%s%nTime: %s%nRecord time: %s", message, time, recordsFileHandler.getRecordTime());
            }
        }
    }

    private void openSurroundingCellsForZeroCell(int cellNumber) {
        ArrayList<Integer> surroundingCellsList = neighborsListsForCells[cellNumber];

        for (Integer currentNeighbor : surroundingCellsList) {
            if (cellsStatusMap[currentNeighbor].equals(CellStatus.UNMARKED)) {
                cellsStatusMap[currentNeighbor] = CellStatus.OPENED;
                openedCellsCount++;
                updatedCellsNumbers.add(currentNeighbor);

                if (minedNeighborsCountsForCells[currentNeighbor] != 0) {
                    continue;
                }

                openSurroundingCellsForZeroCell(currentNeighbor);
            }
        }
    }

    private void generateMinedCellsNumbers(int excludedCellNumber) {
        int minCellNumber = 0;
        int maxCellNumber = rowsCount * columnsCount - 1;
        int minedCellNumber;
        mined = new boolean[rowsCount * columnsCount];

        Random random = new Random();

        minedCellsSet = new TreeSet<>();

        do {
            minedCellNumber = getRandomInteger(random, minCellNumber, maxCellNumber);

            if (minedCellNumber != excludedCellNumber) {
                minedCellsSet.add(minedCellNumber);
                mined[minedCellNumber] = true;
            }
        } while (minedCellsSet.size() < minedCellsCount);
    }

    private int getRandomInteger(Random random, int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    private int[] getCellCoordinatesByCellNumber(int cellNumber) {
        return new int[]{cellNumber / columnsCount, cellNumber % columnsCount};
    }

    private int getCellNumberByCellCoordinates(int rowNumber, int columnNumber) {
        return columnsCount * rowNumber + columnNumber;
    }

    private int getMinedNeighborsCountAndGenerateNeighborsList(int cellNumber, ArrayList<Integer> neighborsList) {
        int[] cellCoordinates = getCellCoordinatesByCellNumber(cellNumber);
        int rowNumber = cellCoordinates[0];
        int columnNumber = cellCoordinates[1];
        int minedNeighborsCount = 0;

        ArrayList<Integer> horizontalIndicesList = new ArrayList<>();
        ArrayList<Integer> verticalIndicesList = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            horizontalIndicesList.add(rowNumber + i);
            verticalIndicesList.add(columnNumber + i);
        }

        for (int e1 : horizontalIndicesList) {
            for (int e2 : verticalIndicesList) {
                if (e1 >= 0 && e1 < rowsCount && e2 >= 0 && e2 < columnsCount && (e1 != rowNumber || e2 != columnNumber)) {
                    int currentNeighbor = getCellNumberByCellCoordinates(e1, e2);
                    neighborsList.add(currentNeighbor);

                    if (minedCellsSet.contains(currentNeighbor)) {
                        minedNeighborsCount++;
                    }
                }
            }
        }

        return minedNeighborsCount;
    }
}