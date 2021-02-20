package ru.academits.java.glushkov.minesweeper.gui;

import javax.swing.*;
import java.awt.*;

public class HighScoresPanel extends JPanel {
    public HighScoresPanel(Object[][] data) {
        String[] columnsNames = {
                "Rows Count",
                "Columns Count",
                "Mines Count",
                "Record Time, hh:mm:ss"
        };

        JTable table = new JTable(data, columnsNames);
        table.setPreferredScrollableViewportSize(new Dimension(600, 200));
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
}