package ru.academits.java.glushkov.csv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Csv {
    private String inputCsvFileName;
    private String outputHtmlFileName;
    private final HashMap<Character, String> map;


    public Csv() {
        map = new HashMap<>();
        map.put('<', "&lt;");
        map.put('>', "&gt;");
        map.put('&', "&amp;");
    }

    public Csv(String inputCsvFileName, String outputHtmlFileName) {
        this();
        this.inputCsvFileName = inputCsvFileName;
        this.outputHtmlFileName = outputHtmlFileName;
    }

    public String getInputCsvFileName() {
        return inputCsvFileName;
    }

    public void setInputCsvFileName(String inputCsvFileName) {
        this.inputCsvFileName = inputCsvFileName;
    }

    public String getOutputHtmlFileName() {
        return outputHtmlFileName;
    }

    public void setOutputHtmlFileName(String outputHtmlFileName) {
        this.outputHtmlFileName = outputHtmlFileName;
    }

    public void convertCsvToHtml() {
        try (Scanner scanner = new Scanner(new FileInputStream(inputCsvFileName));
             PrintWriter writer = new PrintWriter(outputHtmlFileName)) {

            setHtmlDocumentStartingTags(writer);

            StringBuilder token = new StringBuilder();
            boolean isInQuotesToken = false;

            while (scanner.hasNextLine()) {
                token.append(scanner.nextLine());
                String currentCsvLine = token.toString();

                int j = 0;

                while (j < currentCsvLine.length()) {
                    char currentSymbol = currentCsvLine.charAt(j);

                    if (isInQuotesToken) {
                        if (currentSymbol != '"') {
                            writeCurrentSymbol(currentSymbol, writer);
                        } else {
                            int nextSymbolIndex = j + 1;

                            if (nextSymbolIndex >= currentCsvLine.length() && !scanner.hasNextLine()) {
                                setHtmlDocumentEndingTags(writer);

                                return;
                            }

                            if (nextSymbolIndex >= currentCsvLine.length() && scanner.hasNextLine()) {
                                closeCurrentTrTagAndOpenNewTrTag(writer);
                                isInQuotesToken = false;
                            } else if (currentCsvLine.charAt(nextSymbolIndex) == ',') {
                                closeCurrentTdTagAndOpenNewTdTag(writer);
                                isInQuotesToken = false;
                                j++;

                                if (j + 1 == currentCsvLine.length()) {
                                    closeCurrentTrTagAndOpenNewTrTag(writer);
                                }
                            } else {
                                writer.print(currentSymbol);
                                j++;
                            }
                        }
                    } else {
                        if (currentSymbol != '"') {
                            if (currentSymbol == ',') {
                                closeCurrentTdTagAndOpenNewTdTag(writer);
                            } else {
                                writeCurrentSymbol(currentSymbol, writer);
                            }

                            if (j + 1 == currentCsvLine.length() && scanner.hasNextLine()) {
                                closeCurrentTrTagAndOpenNewTrTag(writer);
                            }
                        } else {
                            isInQuotesToken = true;
                        }
                    }

                    j++;
                }

                token.delete(0, token.length());
            }

            setHtmlDocumentEndingTags(writer);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + inputCsvFileName + "\" not found");
        }
    }

    private void closeCurrentTdTagAndOpenNewTdTag(PrintWriter writer) {
        writer.println("</td>");
        writer.print("\t\t\t\t<td>");
    }

    private void closeCurrentTrTagAndOpenNewTrTag(PrintWriter writer) {
        writer.println("</td>");
        writer.println("\t\t\t</tr>");
        writer.println("\t\t\t<tr>");
        writer.print("\t\t\t\t<td>");
    }

    private void setHtmlDocumentStartingTags(PrintWriter writer) {
        //noinspection SpellCheckingInspection
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("\t<head>");
        writer.println("\t\t<meta charset=\"UTF-8\">");
        writer.println("\t</head>");
        writer.println("\t<body>");
        writer.println("\t\t<table border=\"1\">");
        writer.println("\t\t\t<tr>");
        writer.print("\t\t\t\t<td>");
    }

    private void setHtmlDocumentEndingTags(PrintWriter writer) {
        writer.println("</td>");
        writer.println("\t\t\t</tr>");
        writer.println("\t\t</table>");
        writer.println("\t</body>");
        writer.print("</html>");
    }

    private void writeCurrentSymbol(char currentSymbol, PrintWriter writer) {
        if (currentSymbol != '<' && currentSymbol != '>' && currentSymbol != '&') {
            writer.print(currentSymbol);
        } else {
            writer.print(map.get(currentSymbol));
        }
    }
}