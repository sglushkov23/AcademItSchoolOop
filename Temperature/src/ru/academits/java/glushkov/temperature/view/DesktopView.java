package ru.academits.java.glushkov.temperature.view;

import ru.academits.java.glushkov.temperature.controller.Controller;
import ru.academits.java.glushkov.temperature.model.Scale;

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
    private final Scale[] scales;
    private final HashMap<Scale, Scale[]> map;
    private String inputText;
    private Scale inputPanelOldScale;
    private Scale outputPanelOldScale;
    private boolean isAllowedToListen;

    public DesktopView(Controller controller) {
        this.controller = controller;
        scales = this.controller.getConverter().getScales();
        map = new HashMap<>();

        for (Scale scale : scales) {
            map.put(scale, getRemainingScales(scales, scale));
        }

        inputText = "";
        inputPanelOldScale = scales[0];
        outputPanelOldScale = scales[1];
        isAllowedToListen = true;
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            //JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame frame = new JFrame("Temperature Converter");
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            inputPanel = new InputOutputPanel("Input Panel", scales);
            outputPanel = new InputOutputPanel("Output Panel", getRemainingScales(scales, scales[0]));

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

            inputPanel.getScaleChooser().addActionListener(e -> {
                Scale scale = (Scale) inputPanel.getScaleChooser().getSelectedItem();

                if (!inputPanelOldScale.equals(scale)) {
                    outputPanel.setText("");

                    isAllowedToListen = false;
                    outputPanel.getScaleChooser().removeAllItems();

                    for (int i = 0; i < map.size() - 1; i++) {
                        outputPanel.getScaleChooser().insertItemAt(map.get(scale)[i], i);
                    }

                    isAllowedToListen = true;
                    outputPanel.getScaleChooser().setSelectedIndex(0);

                    inputPanelOldScale = scale;
                }
            });

            outputPanel.getScaleChooser().addActionListener(e -> {
                if (!isAllowedToListen) {
                    return;
                }

                Scale scale = (Scale) outputPanel.getScaleChooser().getSelectedItem();

                if (!outputPanelOldScale.equals(scale)) {
                    outputPanel.setText("");
                    outputPanelOldScale = scale;
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
                    if (!inputText.equals(inputPanel.getText())) {
                        outputPanel.setText("");
                    }
                }
            });

            convertButton.addActionListener(e -> {
                try {
                    inputText = inputPanel.getText();
                    double temperature = Double.parseDouble(inputText);
                    controller.convert(
                            temperature,
                            (Scale) Objects.requireNonNull(inputPanel.getScaleChooser().getSelectedItem()),
                            (Scale) outputPanel.getScaleChooser().getSelectedItem());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Temperature must be a number");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            });

            frame.setContentPane(mainPanel);
            frame.pack();
            frame.setMinimumSize(frame.getSize());
            frame.setVisible(true);
        });
    }

    @Override
    public void setOutputTemperature(double temperature) {
        outputPanel.setText(String.format("%.2f", temperature));
    }

    private Scale[] getRemainingScales(Scale[] scales, Scale scale) {
        Scale[] remainingScales = new Scale[scales.length - 1];

        int i = 0;

        for (Scale e : scales) {
            if (e == scale) {
                continue;
            }

            remainingScales[i] = e;
            i++;
        }

        return remainingScales;
    }
}