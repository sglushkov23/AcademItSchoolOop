package ru.academits.java.glushkov.minesweeper.model;

import java.io.*;
import java.util.*;

class RecordsFileHandler {
    private static final String GAME_RESULTS_FILE_PATH = "Minesweeper\\src\\ru\\academits\\java\\glushkov\\minesweeper\\resources\\records_file.csv";
    private final int rowsCount;
    private final int columnsCount;
    private final int minedCellsCount;
    private final String time;
    private String recordTime;
    private boolean isRecord;

    public RecordsFileHandler(int rowsCount, int columnsCount, int minedCellsCount, String time) {
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.minedCellsCount = minedCellsCount;
        this.time = time;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public boolean isRecord() {
        return isRecord;
    }

    public void writeResultToFile() {
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

    public void checkRecord() {
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

    public static String[][] getRecordDataForEachLevel() {
        String[][] result = null;

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

            result = new String[gameParametersToRecordMap.size()][4];

            int i = 0;

            for (Integer[] k : gameParametersToRecordMap.keySet()) {
                int j = 0;

                for (int e : k) {
                    result[i][j] = String.valueOf(e);
                    j++;
                }

                result[i][k.length] = gameParametersToRecordMap.get(k);
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found%n", GAME_RESULTS_FILE_PATH);
        }

        if (result == null) {
            return new String[][]{};
        }

        return result;
    }

    private static Integer[] getGameParametersArray(String gameParameters) {
        String[] splitGameParameters = gameParameters.split(",");
        Integer[] gameParametersArray = new Integer[splitGameParameters.length];

        int i = 0;

        for (String s : splitGameParameters) {
            gameParametersArray[i] = Integer.parseInt(s);
            i++;
        }

        return gameParametersArray;
    }
}