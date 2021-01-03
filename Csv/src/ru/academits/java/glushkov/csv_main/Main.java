package ru.academits.java.glushkov.csv_main;

import ru.academits.java.glushkov.csv.Csv;

public class Main {
    public static void main(String[] args) {
        Csv csv = new Csv(args[0], args[1]);
        csv.convertCsvToHtml();
    }
}