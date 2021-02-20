package ru.academits.java.glushkov.minesweeper.gui;

import javax.swing.*;
import java.awt.*;

public class CustomLevelSelectionPanel extends JPanel {
    private final SliderPanel rowsCountSliderPanel;
    private final SliderPanel columnsCountSliderPanel;
    private final SliderPanel minesCountSliderPanel;

    public CustomLevelSelectionPanel() {
        int minRowsCount = 9;
        int maxRowsCount = 24;
        int minColumnsCount = 9;
        int maxColumnsCount = 30;
        int minMinesCount = 10;
        int maxMinesCount = getMaxMinesCount(minRowsCount, minColumnsCount);

        rowsCountSliderPanel = new SliderPanel("Select rows number", minRowsCount, maxRowsCount);
        columnsCountSliderPanel = new SliderPanel("Select columns number", minColumnsCount, maxColumnsCount);
        minesCountSliderPanel = new SliderPanel("Select mines number", minMinesCount, maxMinesCount);

        rowsCountSliderPanel.getSlider().addChangeListener(e -> changeMaxMinesCount());

        columnsCountSliderPanel.getSlider().addChangeListener(e -> changeMaxMinesCount());

        setLayout(new GridLayout(3, 1));
        add(rowsCountSliderPanel);
        add(columnsCountSliderPanel);
        add(minesCountSliderPanel);
    }

    public int getRowsCount() {
        return rowsCountSliderPanel.getValue();
    }

    public int getColumnsCount() {
        return columnsCountSliderPanel.getValue();
    }

    public int getMinesCount() {
        return minesCountSliderPanel.getValue();
    }

    private void changeMaxMinesCount() {
        minesCountSliderPanel.getSlider().setMaximum(getMaxMinesCount(rowsCountSliderPanel.getValue(), columnsCountSliderPanel.getValue()));
    }

    private int getMaxMinesCount(int rowsCount, int columnsCount) {
        return (int) (0.9 * rowsCount * columnsCount);
    }
}
