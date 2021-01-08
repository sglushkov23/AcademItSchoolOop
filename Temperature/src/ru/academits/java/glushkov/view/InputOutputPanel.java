package ru.academits.java.glushkov.view;

import javax.swing.*;
import java.awt.*;

public class InputOutputPanel extends JPanel {
    private final JTextField textField;
    private final JComboBox<String> unitChooser;

    public InputOutputPanel(String title, String[] units) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        textField = new JTextField(10);
        textField.setFont(new Font("CourierNew", Font.BOLD, 42));

        unitChooser = new JComboBox<>(units);
        unitChooser.setFont(new Font("CourierNew", Font.BOLD, 32));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(textField);
        add(unitChooser);
        textField.setAlignmentX(LEFT_ALIGNMENT);
        unitChooser.setAlignmentX(LEFT_ALIGNMENT);
    }

    public JTextField getTextField() {
        return textField;
    }

    public JComboBox<String> getUnitChooser() {
        return unitChooser;
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