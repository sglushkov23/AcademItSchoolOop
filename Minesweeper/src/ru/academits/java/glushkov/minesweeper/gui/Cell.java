package ru.academits.java.glushkov.minesweeper.gui;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {
    private final int size;

    public Cell(int size) {
        this.size = size;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
}