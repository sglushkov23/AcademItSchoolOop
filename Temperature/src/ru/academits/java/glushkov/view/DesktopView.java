package ru.academits.java.glushkov.view;

import ru.academits.java.glushkov.controller.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

public class DesktopView implements View {
    private final Controller controller;
    private InputOutputPanel inputPanel;
    private InputOutputPanel outputPanel;
    private final HashMap<String, String[]> map;
    private String inputText;
    private String inputPanelOldUnit;
    private String outputPanelOldUnit;
    private boolean isAllowedToListen;

    public DesktopView(Controller controller) {
        map = new HashMap<>();
        map.put("Celsius", new String[]{"Fahrenheit", "Kelvin"});
        map.put("Fahrenheit", new String[]{"Celsius", "Kelvin"});
        map.put("Kelvin", new String[]{"Celsius", "Fahrenheit"});
        this.controller = controller;
        inputText = "";
        inputPanelOldUnit = "Celsius";
        outputPanelOldUnit = "Fahrenheit";
        isAllowedToListen = true;
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame frame = new JFrame("Temperature Converter");
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            inputPanel = new InputOutputPanel("Input Panel", new String[]{"Celsius", "Fahrenheit", "Kelvin"});
            outputPanel = new InputOutputPanel("Output Panel", new String[]{"Fahrenheit", "Kelvin"});

            outputPanel.getTextField().setEnabled(false);
            outputPanel.getTextField().setDisabledTextColor(new Color(0, 0, 0));

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());

            int inset = 10;

            GridBagConstraints c1 = new GridBagConstraints();
            c1.fill = GridBagConstraints.HORIZONTAL;
            c1.gridwidth = 2;
            c1.gridx = 0;
            c1.gridy = 0;
            c1.insets = new Insets(inset, inset, inset, inset);
            mainPanel.add(inputPanel, c1);

            GridBagConstraints c2 = new GridBagConstraints();
            c2.fill = GridBagConstraints.HORIZONTAL;
            c2.gridwidth = 2;
            c2.gridx = 2;
            c2.gridy = 0;
            c2.insets = new Insets(inset, inset, inset, inset);
            mainPanel.add(outputPanel, c2);

            JButton convertButton = new JButton("Convert Temperature");
            convertButton.setFont(new Font("CourierNew", Font.BOLD, 32));
            Dimension buttonDimension = new Dimension(inset + inputPanel.getPreferredSize().width / 2, convertButton.getPreferredSize().height);
            convertButton.setPreferredSize(buttonDimension);

            GridBagConstraints c3 = new GridBagConstraints();
            c3.fill = GridBagConstraints.HORIZONTAL;
            c3.weightx = 0;
            c3.gridwidth = 1;
            c3.gridy = 1;

            c3.gridx = 0;
            mainPanel.add(Box.createRigidArea(buttonDimension), c3);
            c3.gridx = 1;
            mainPanel.add(Box.createRigidArea(buttonDimension), c3);
            c3.gridx = 2;
            mainPanel.add(Box.createRigidArea(buttonDimension), c3);
            c3.gridx = 3;
            mainPanel.add(Box.createRigidArea(buttonDimension), c3);

            GridBagConstraints c4 = new GridBagConstraints();
            c4.fill = GridBagConstraints.HORIZONTAL;
            c4.gridwidth = 2;
            c4.gridx = 1;
            c4.gridy = 1;
            c4.insets = new Insets(inset, inset, inset, inset);
            mainPanel.add(convertButton, c4);

            inputPanel.getUnitChooser().addActionListener(e -> {
                String unit = (String) inputPanel.getUnitChooser().getSelectedItem();

                if (!inputPanelOldUnit.equals(unit)) {
                    outputPanel.getTextField().setText("");

                    isAllowedToListen = false;
                    outputPanel.getUnitChooser().removeAllItems();
                    outputPanel.getUnitChooser().insertItemAt(map.get(unit)[0], 0);
                    outputPanel.getUnitChooser().insertItemAt(map.get(unit)[1], 1);

                    isAllowedToListen = true;
                    outputPanel.getUnitChooser().setSelectedIndex(0);

                    inputPanelOldUnit = unit;
                }
            });

            outputPanel.getUnitChooser().addActionListener(e -> {
                if (!isAllowedToListen) {
                    return;
                }

                String unit = (String) outputPanel.getUnitChooser().getSelectedItem();

                if (!outputPanelOldUnit.equals(unit)) {
                    outputPanel.getTextField().setText("");
                    outputPanelOldUnit = unit;
                }
            });

            inputPanel.getTextField().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    clear();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    clear();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    clear();
                }

                public void clear() {
                    if (!inputText.equals(inputPanel.getTextField().getText())) {
                        outputPanel.getTextField().setText("");
                    }
                }
            });

            convertButton.addActionListener(e -> {
                try {
                    inputText = inputPanel.getTextField().getText();
                    double temperature = Double.parseDouble(inputText);
                    controller.convertTemperature(
                            temperature,
                            (String) Objects.requireNonNull(inputPanel.getUnitChooser().getSelectedItem()),
                            (String) outputPanel.getUnitChooser().getSelectedItem());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Temperature must be a number");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            });

            frame.setContentPane(mainPanel);
            frame.pack();
            frame.setVisible(true);
        });
    }

    @Override
    public void setTemperature(double temperature) {
        outputPanel.getTextField().setText(String.format("%.2f", temperature));
    }
}