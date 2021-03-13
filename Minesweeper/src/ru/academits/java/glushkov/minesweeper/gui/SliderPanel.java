package ru.academits.java.glushkov.minesweeper.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderPanel {
    private final JSlider slider;
    private final JTextField textField;
    private final JPanel panel;

    public SliderPanel(String title, int min, int max) {
        panel = new JPanel();

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        slider = getSlider(min, max);
        slider.addChangeListener(new SliderPanelChangeListener());
        textField = new JTextField(3);
        textField.setText(String.format("%d", min));
        textField.setEnabled(false);
        textField.setDisabledTextColor(new Color(0, 0, 0));

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(slider);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(textField);
        textField.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
        slider.setAlignmentY(JPanel.BOTTOM_ALIGNMENT);
    }

    public JPanel getPanel() {
        return panel;
    }

    public int getValue() {
        return slider.getValue();
    }

    public JSlider getSlider() {
        return slider;
    }

    private JSlider getSlider(int minValue, int maxValue) {
        JSlider slider = new JSlider(minValue, maxValue);
        slider.setMajorTickSpacing(1);
        slider.setValue(minValue);

        return slider;
    }

    private class SliderPanelChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            textField.setText(String.format("%d", slider.getValue()));
        }
    }
}