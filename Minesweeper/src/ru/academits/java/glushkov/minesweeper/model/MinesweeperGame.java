package ru.academits.java.glushkov.minesweeper.model;

import java.io.*;
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
    private String recordTime;
    private boolean isRecord;
    private static final String GAME_RESULTS_FILE_PATH = "Minesweeper\\src\\ru\\academits\\java\\glushkov\\minesweeper\\resources\\records_file.csv";

    public MinesweeperGame() {
    }

    @Override
    public void setParameters(int rowsCount, int columnsCount, int minedCellsCount) {
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.minedCellsCount = minedCellsCount;

        cellsStatusMap = new CellStatus[this.rowsCount * this.columnsCount];
        Arrays.fill(cellsStatusMap, CellStatus.UNMARKED);

        isInitialized = false;
        isOver = false;
        isRecord = false;
    }

    @Override
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public Object[][] getRecordData() {
        return getRecordDataForEachLevel();
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
        message = this.isWin ? "Поздравляем!" : "В следующий раз повезет больше";

        if (this.isWin) {
            checkRecord();
            writeResultToFile();

            if (isRecord) {
                message = String.format("%s%nНовый рекорд: %s%nПредыдущий рекорд: %s", message, time, recordTime);
            } else {
                message = String.format("%s%nВремя: %s%nРекордное время: %s", message, time, recordTime);
            }
        }
    }

    private void openSurroundingCellsForZeroCell(int cellNumber) {
        ArrayList<Integer> surroundingCellsList = neighborsListsForCells[cellNumber];

        for (Integer currentNeighbor : surroundingCellsList) {
            if (cellsStatusMap[currentNeighbor].equals(CellStatus.UNMARKED)) {
                cellsStatusMap[currentNeighbor] = CellStatus.OPENED;
                openedCellsCount++;
                System.out.println("opened cells count = " + openedCellsCount);
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

    private void writeResultToFile() {
        System.out.println("writing");
        System.out.println("time = " + time);

        try (PrintWriter writer = new PrintWriter(new FileWriter(GAME_RESULTS_FILE_PATH, true))) {
            String line = String.format("%d,%d,%d,%s", rowsCount, columnsCount, minedCellsCount, time);
            writer.println(line);
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found%n", GAME_RESULTS_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkRecord() {
        try (Scanner scanner = new Scanner(new FileInputStream(GAME_RESULTS_FILE_PATH))) {
            String currentGameParameters = String.format("%d,%d,%d,", rowsCount, columnsCount, minedCellsCount);
            String line;
            ArrayList<String> oldGamesTimesList = new ArrayList<>();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line.startsWith(currentGameParameters)) {
                    oldGamesTimesList.add(line.split(",")[3]);
                }
            }

            if (oldGamesTimesList.size() == 0) {
                recordTime = time;
            } else {
                recordTime = Collections.min(oldGamesTimesList);
            }

            if (time.compareTo(recordTime) < 0) {
                isRecord = true;
            }
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found%n", GAME_RESULTS_FILE_PATH);
        }
    }

    private Integer[] getGameParametersArray(String gameParameters) {
        String[] splitGameParameters = gameParameters.split(",");
        Integer[] gameParametersArray = new Integer[splitGameParameters.length];

        int i = 0;

        for (String s : splitGameParameters) {
            gameParametersArray[i] = Integer.parseInt(s);
            i++;
        }

        return gameParametersArray;
    }

    private Object[][] getRecordDataForEachLevel() {
        Object[][] result = null;

        try (Scanner scanner = new Scanner(new FileInputStream(GAME_RESULTS_FILE_PATH))) {
            String line;
            Integer[] key;
            String value;
            int timeSubstringLength = 8;
            TreeMap<Integer[], String> gameParametersToRecordMap = new TreeMap<>((o1, o2) -> {
                int size = o1.length;

                for (int i = 0; i < size; i++) {
                    int comparingResult = o1[i].compareTo(o2[i]);

                    if (comparingResult != 0) {
                        return comparingResult;
                    }
                }

                return 0;
            });

            scanner.nextLine();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                int lineLength = line.length();
                key = getGameParametersArray(line.substring(0, lineLength - timeSubstringLength - 1));
                value = line.substring(lineLength - timeSubstringLength, lineLength);

                if (gameParametersToRecordMap.containsKey(key)) {
                    if (value.compareTo(gameParametersToRecordMap.get(key)) < 0) {
                        gameParametersToRecordMap.replace(key, value);
                    }
                } else {
                    gameParametersToRecordMap.put(key, value);
                }
            }

            result = new Object[gameParametersToRecordMap.size()][4];

            int i = 0;

            for (Integer[] k : gameParametersToRecordMap.keySet()) {
                System.out.println(Arrays.toString(k));
                System.arraycopy(k, 0, result[i], 0, k.length);
                result[i][k.length] = gameParametersToRecordMap.get(k);
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found%n", GAME_RESULTS_FILE_PATH);
        }

        if (result == null) {
            return new Object[][]{};
        }

        return result;
    }
}