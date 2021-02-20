package ru.academits.java.glushkov.minesweeper.gui;

import javax.swing.*;
import java.awt.*;

public class CountersPanel extends JPanel {
    private final JLabel timeLabel;
    private final JLabel minesCountLabel;
    ImageIcon watchIcon;
    ImageIcon mineIcon;

    public CountersPanel(String title, int startingMinesCount) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );

        setOpaque(false);

        IconsCreator iconsCreator = new IconsCreator();
        Font font = new Font("CourierNew", Font.BOLD, 32);

        String path1 = "..\\resources\\timer_icon.png";
        watchIcon = iconsCreator.createImageIcon(path1, "watch icon");

        String path2 = "..\\resources\\mine_icon.png";
        mineIcon = iconsCreator.createImageIcon(path2, "mine icon");

        timeLabel = new JLabel("00:00:00", watchIcon, JLabel.LEFT);
        timeLabel.setHorizontalTextPosition(JLabel.RIGHT);
        timeLabel.setFont(font);

        minesCountLabel = new JLabel(String.format("%d", startingMinesCount), mineIcon, JLabel.LEFT);
        minesCountLabel.setHorizontalTextPosition(JLabel.RIGHT);
        minesCountLabel.setFont(font);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createGlue());
        add(timeLabel);
        add(Box.createVerticalStrut(5));
        add(minesCountLabel);
        add(Box.createGlue());

        timeLabel.setAlignmentX(CENTER_ALIGNMENT);
        minesCountLabel.setAlignmentX(CENTER_ALIGNMENT);
    }

    public void setMinesCount(int minesCount) {
        minesCountLabel.setText(String.format("%d", minesCount));
    }

    public int getMinesCount() {
        return Integer.parseInt(minesCountLabel.getText());
    }

    public void setCurrentTime(String time) {
        timeLabel.setText(time);
    }

    public String getCurrentTime() {
        return timeLabel.getText();
    }

    public int getIconsHeight() {
        return watchIcon.getIconHeight() + mineIcon.getIconHeight();
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
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