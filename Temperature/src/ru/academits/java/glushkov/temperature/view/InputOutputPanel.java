package ru.academits.java.glushkov.temperature.view;

import ru.academits.java.glushkov.temperature.model.Scale;

import javax.swing.*;
import java.awt.*;

public class InputOutputPanel extends JPanel {
    private final JTextField textField;
    private final JComboBox<Scale> scaleChooser;

    public InputOutputPanel(String title, Scale[] scales) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        textField = new JTextField(10);
        textField.setFont(new Font("CourierNew", Font.BOLD, 42));

        scaleChooser = new JComboBox<>(scales);
        scaleChooser.setFont(new Font("CourierNew", Font.BOLD, 32));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(textField);
        add(scaleChooser);
        textField.setAlignmentX(LEFT_ALIGNMENT);
        scaleChooser.setAlignmentX(LEFT_ALIGNMENT);
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public String getText() {
        return textField.getText();
    }

    public JTextField getTextField() {
        return textField;
    }

    public JComboBox<Scale> getScaleChooser() {
        return scaleChooser;
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