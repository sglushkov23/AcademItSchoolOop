package ru.academits.java.glushkov.csv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Csv {
    public void convertCsvToHtml(String inputCsvFileName, String outputHtmlFileName) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream(inputCsvFileName));
             PrintWriter writer = new PrintWriter(outputHtmlFileName)) {
            setHtmlDocumentStartingTags(writer, outputHtmlFileName);

            boolean isInQuotesToken = false;
            boolean needToCloseCurrentTrTagAndOpenNewTrTag = false;

            while (scanner.hasNextLine()) {
                String currentCsvLine = scanner.nextLine();

                if (isInQuotesToken && !currentCsvLine.equals("")) {
                    writer.print("<br>");
                }

                if (needToCloseCurrentTrTagAndOpenNewTrTag && !currentCsvLine.equals("")) {
                    closeCurrentTrTagAndOpenNewTrTag(writer);
                    needToCloseCurrentTrTagAndOpenNewTrTag = false;
                }

                int i = 0;

                while (i < currentCsvLine.length()) {
                    char currentSymbol = currentCsvLine.charAt(i);

                    if (isInQuotesToken) {
                        if (currentSymbol != '"') {
                            writeCurrentSymbol(currentSymbol, writer);
                        } else {
                            int nextSymbolIndex = i + 1;

                            if (nextSymbolIndex >= currentCsvLine.length() && !scanner.hasNextLine()) {
                                setHtmlDocumentEndingTags(writer);

                                return;
                            }

                            if (nextSymbolIndex >= currentCsvLine.length() && scanner.hasNextLine()) {
                                needToCloseCurrentTrTagAndOpenNewTrTag = true;
                                isInQuotesToken = false;
                            } else if (currentCsvLine.charAt(nextSymbolIndex) == ',') {
                                closeCurrentTdTagAndOpenNewTdTag(writer);
                                isInQuotesToken = false;
                                i++;

                                if (i + 1 == currentCsvLine.length()) {
                                    closeCurrentTrTagAndOpenNewTrTag(writer);
                                }
                            } else {
                                writer.print(currentSymbol);
                                i++;
                            }
                        }
                    } else {
                        if (currentSymbol != '"') {
                            if (currentSymbol == ',') {
                                closeCurrentTdTagAndOpenNewTdTag(writer);
                            } else {
                                writeCurrentSymbol(currentSymbol, writer);
                            }

                            if (i + 1 == currentCsvLine.length() && scanner.hasNextLine()) {
                                needToCloseCurrentTrTagAndOpenNewTrTag = true;
                            }
                        } else {
                            isInQuotesToken = true;
                        }
                    }

                    i++;
                }
            }

            setHtmlDocumentEndingTags(writer);
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

    private void setHtmlDocumentStartingTags(PrintWriter writer, String title) {
        //noinspection SpellCheckingInspection
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("\t<head>");
        writer.println("\t\t<meta charset=\"UTF-8\">");
        writer.println("\t\t<title>" + title + "</title>");
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
        if (currentSymbol == '<') {
            writer.print("&lt;");
        } else if (currentSymbol == '>') {
            writer.print("&gt;");
        } else if (currentSymbol == '&') {
            writer.print("&amp;");
        } else {
            writer.print(currentSymbol);
        }
    }
}