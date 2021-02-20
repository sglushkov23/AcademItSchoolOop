package ru.academits.java.glushkov.minesweeper.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderPanel extends JPanel{
    private final JSlider slider;
    private final JTextField textField;

    public SliderPanel(String title, int min, int max) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        slider = getSlider(min, max);
        slider.addChangeListener(new SliderPanelChangeListener());
        textField = new JTextField(3);
        textField.setText(String.format("%d", min));
        textField.setEnabled(false);
        textField.setDisabledTextColor(new Color(0, 0, 0));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(slider);
        add(Box.createHorizontalStrut(5));
        add(textField);
        textField.setAlignmentY(BOTTOM_ALIGNMENT);
        slider.setAlignmentY(BOTTOM_ALIGNMENT);
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