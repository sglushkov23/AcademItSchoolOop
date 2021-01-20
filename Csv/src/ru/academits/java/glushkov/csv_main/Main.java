package ru.academits.java.glushkov.csv_main;

import ru.academits.java.glushkov.csv.Csv;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("-help")) {
            displayHelp();

            return;
        }

        if (args.length != 2) {
            System.out.println("Number of arguments passed (" + (args.length) + ") is incorrect");
            System.out.println();
            displayHelp();

            return;
        }

        Csv csv = new Csv();

        try {
            csv.convertCsvToHtml(args[0], args[1]);
        } catch (FileNotFoundException e) {
            System.out.printf("File \"%s\" not found or names of files \"%s\" and/or \"%s\" contain syntax error%n",
                    args[0], args[0], args[1]);
        }
    }

    public static void displayHelp() {
        System.out.println("In order to convert CSV file to HTML file you should pass two arguments:");
        System.out.println("[drive1:][path1]csv_input_file_name [drive2:][path2]html_output_file_name");
    }
}