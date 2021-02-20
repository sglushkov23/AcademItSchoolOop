package ru.academits.java.glushkov.minesweeper.gui;

import ru.academits.java.glushkov.minesweeper.controller.Controller;
import ru.academits.java.glushkov.minesweeper.model.CellStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DesktopView implements View {
    private final Controller controller;
    private Level level;
    private int rowsCount;
    private int columnsCount;
    private int minedCellsCount;
    private boolean isAllowedToListen = true;
    JButton[] cells;
    JFrame frame;
    private int mainWindowWidth;
    private int mainWindowHeight;
    private int gamePanelWidth;
    private int gamePanelHeight;
    private int levelSelectionPanelWidth;
    private int levelSelectionPanelHeight;
    private int cellSize;
    private ImageIcon flagIcon;
    private ImageIcon questionIcon;
    private ImageIcon mineIcon;
    private ImageIcon explosionIcon;
    private boolean isShownGamePanel;
    private boolean isFirstClick;
    private GridBagConstraints gamePanelConstraints;
    private GridBagConstraints countersPanelConstraints;
    private GridBagConstraints commandButtonsPanelConstraints;
    private GridBagConstraints levelSelectionPanelConstraints;
    private JPanel mainPanel;
    private JPanel levelSelectionPanel;
    private JPanel gamePanel;
    private CustomLevelSelectionPanel customLevelSelectionPanel;
    private CountersPanel countersPanel;
    private Thread stopwatchThread;
    private final int commandButtonHeight = 50;
    private final int commandButtonsCount = 4;

    public DesktopView(Controller controller) {
        this.controller = controller;
    }

    private class NumberedMouseListener extends MouseAdapter {
        private final int number;

        public NumberedMouseListener(int number) {
            this.number = number;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!isAllowedToListen) {
                return;
            }

            if (e.getButton() == MouseEvent.BUTTON1) {
                controller.updateOnLeftClick(number);

                if (isFirstClick) {
                    stopwatchThread = new Thread(new Stopwatch(countersPanel));
                    stopwatchThread.start();
                    isFirstClick = false;
                }

                return;
            }

            if (e.getButton() == MouseEvent.BUTTON2) {
                return;
            }

            controller.updateOnRightClick(number);
        }
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            setMainWindowGeometricalParameters();
            setConstraintsForMainPanelAndCommandButtons();

            mainPanel = new JPanel();
            GridBagLayout gridBagLayout = new GridBagLayout();
            mainPanel.setLayout(gridBagLayout);

            levelSelectionPanel = new JPanel();
            levelSelectionPanel.setLayout(new GridLayout(1, 4, 10, 10));
            Dimension dimension = new Dimension(levelSelectionPanelWidth, levelSelectionPanelHeight);
            levelSelectionPanel.setPreferredSize(dimension);
            levelSelectionPanel.setMinimumSize(dimension);
            levelSelectionPanel.setMaximumSize(dimension);
            levelSelectionPanel.setOpaque(false);
            levelSelectionPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("SELECT LEVEL"),
                    BorderFactory.createEmptyBorder(10, 5, 5, 5))
            );

            String formatString = "<html><p style=\"text-align:center\">%s</p></html>";
            JButton simpleButton = new JButton(String.format(formatString, "simple<br>9 x 9"));
            JButton mediumButton = new JButton(String.format(formatString, "medium<br>16 x 16"));
            JButton hardButton = new JButton(String.format(formatString, "hard<br>16 x 30"));
            JButton customButton = new JButton("custom");

            Font font = new Font("CourierNew", Font.BOLD, 32);
            simpleButton.setFont(font);
            mediumButton.setFont(font);
            hardButton.setFont(font);
            customButton.setFont(font);

            simpleButton.addActionListener(e -> {
                level = Level.SIMPLE;
                showGamePanel();
                showCountersPanel();
            });

            mediumButton.addActionListener(e -> {
                level = Level.MEDIUM;
                showGamePanel();
                showCountersPanel();
            });

            hardButton.addActionListener(e -> {
                level = Level.HARD;
                showGamePanel();
                showCountersPanel();
            });

            customButton.addActionListener(e -> {
                level = Level.CUSTOM;

                if (customLevelSelectionPanel == null) {
                    customLevelSelectionPanel = new CustomLevelSelectionPanel();
                }

                int dialogResult = JOptionPane.showOptionDialog(
                        frame,
                        customLevelSelectionPanel,
                        "Field Parameters Selection",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, null, null
                );

                if (dialogResult == JOptionPane.OK_OPTION) {
                    rowsCount = customLevelSelectionPanel.getRowsCount();
                    columnsCount = customLevelSelectionPanel.getColumnsCount();
                    minedCellsCount = customLevelSelectionPanel.getMinesCount();
                    showGamePanel();
                    showCountersPanel();
                }
            });

            levelSelectionPanel.add(simpleButton);
            levelSelectionPanel.add(mediumButton);
            levelSelectionPanel.add(hardButton);
            levelSelectionPanel.add(customButton);

            JPanel commandButtonsPanel = new JPanel();
            commandButtonsPanel.setLayout(new GridLayout(4, 1, 10, 10));

            Dimension dimension1 = new Dimension(mainWindowWidth / 6, commandButtonHeight * commandButtonsCount);
            commandButtonsPanel.setPreferredSize(dimension1);
            commandButtonsPanel.setMinimumSize(dimension1);
            commandButtonsPanel.setMaximumSize(dimension1);
            commandButtonsPanel.setOpaque(false);

            JButton exitButton = new JButton("Exit");
            JButton aboutButton = new JButton("About");
            JButton newGameButton = new JButton("New Game");
            JButton highScoresButton = new JButton("High Scores");

            commandButtonsPanel.add(exitButton);
            commandButtonsPanel.add(aboutButton);
            commandButtonsPanel.add(newGameButton);
            commandButtonsPanel.add(highScoresButton);

            exitButton.addActionListener(e -> System.exit(0));

            newGameButton.addActionListener(e -> {
                if (isShownGamePanel) {
                    mainPanel.remove(gamePanel);
                    mainPanel.remove(countersPanel);
                    mainPanel.add(levelSelectionPanel, levelSelectionPanelConstraints);
                    mainPanel.validate();
                    mainPanel.repaint();
                    isShownGamePanel = false;
                    isAllowedToListen = true;
                }
            });

            highScoresButton.addActionListener(e -> {
                HighScoresPanel highScoresPanel = new HighScoresPanel(controller.getRecordData());

                JOptionPane.showOptionDialog(
                        frame,
                        highScoresPanel,
                        "High Scores",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        null,
                        null
                );
            });

            mainPanel.setBackground(new Color(125, 125, 125));
            mainPanel.add(levelSelectionPanel, levelSelectionPanelConstraints);
            mainPanel.add(commandButtonsPanel, commandButtonsPanelConstraints);

            frame = new JFrame("Minesweeper");
            frame.setSize(mainWindowWidth, mainWindowHeight);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setContentPane(mainPanel);
            frame.setMinimumSize(frame.getSize());
            frame.setVisible(true);
        });
    }

    @Override
    public void updateCell(int cellNumber, String data, int activeMinesCount) {
        if (data.equals(CellStatus.FLAGGED.toString())) {
            cells[cellNumber].setIcon(flagIcon);
        } else if (data.equals(CellStatus.QUESTIONED.toString())) {
            cells[cellNumber].setIcon(questionIcon);
        } else {
            if (data.equals("0")) {
                cells[cellNumber].setBackground(new Color(255, 255, 255));
            }

            cells[cellNumber].setIcon(null);
            cells[cellNumber].setText(data);
        }

        if (countersPanel.getMinesCount() != activeMinesCount) {
            countersPanel.setMinesCount(activeMinesCount);
        }
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    @Override
    public void showMinedCell(int cellNumber, boolean isWin) {
        if (isWin) {
            cells[cellNumber].setIcon(mineIcon);
        } else {
            cells[cellNumber].setIcon(explosionIcon);
        }
    }

    @Override
    public void disableField() {
        stopwatchThread.interrupt();
        isAllowedToListen = false;
    }

    @Override
    public String getTime() {
        return countersPanel.getCurrentTime();
    }

    private void createIcons() {
        double resizeCoefficient = 0.8;
        int iconSize = (int) (resizeCoefficient * cellSize);
        String path = "..\\resources\\";

        IconsCreator iconsCreator = new IconsCreator();

        flagIcon = iconsCreator.createImageIcon(path + "flag.png", "flag", iconSize);
        questionIcon = iconsCreator.createImageIcon(path + "question_mark.png", "question", iconSize);
        mineIcon = iconsCreator.createImageIcon(path + "mine.png", "mine", iconSize);
        explosionIcon = iconsCreator.createImageIcon(path + "explosion.png", "exploded_mine", iconSize);
    }

    private void setGameParameters() {
        if (level.equals(Level.SIMPLE)) {
            rowsCount = 9;
            columnsCount = 9;
            minedCellsCount = 10;
        } else if (level.equals(Level.MEDIUM)) {
            rowsCount = 16;
            columnsCount = 16;
            minedCellsCount = 40;
        } else if (level.equals(Level.HARD)) {
            rowsCount = 16;
            columnsCount = 30;
            minedCellsCount = 99;
        }

        controller.setGameParameters(rowsCount, columnsCount, minedCellsCount);
    }

    private void setMainWindowGeometricalParameters() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindowWidth = screenSize.width;
        int taskBarHeight = 50;
        mainWindowHeight = screenSize.height - taskBarHeight;

        levelSelectionPanelWidth = (int) (0.95 * mainWindowWidth * 4 / 5);
        levelSelectionPanelHeight = levelSelectionPanelWidth / 4 - 1;
    }

    private void setGamePanelGeometricalParameters() {
        if (rowsCount == columnsCount) {
            cellSize = mainWindowHeight / rowsCount - 1;
        } else {
            cellSize = mainWindowWidth * 4 / 5 / columnsCount - 1;

            if (cellSize * rowsCount > mainWindowHeight) {
                cellSize = mainWindowHeight / rowsCount - 1;
            }
        }

        gamePanelWidth = cellSize * columnsCount;
        //gamePanelHeight = cellSize * rowsCount;
        gamePanelHeight = mainWindowHeight;
    }

    private void setConstraintsForMainPanelAndCommandButtons() {
        int inset = 10;

        commandButtonsPanelConstraints = new GridBagConstraints();
        commandButtonsPanelConstraints.fill = GridBagConstraints.BOTH;
        commandButtonsPanelConstraints.weightx = 0.5;
        commandButtonsPanelConstraints.weighty = 0.5;
        commandButtonsPanelConstraints.gridwidth = mainWindowWidth / 6;
        commandButtonsPanelConstraints.gridheight = commandButtonHeight * commandButtonsCount;
        commandButtonsPanelConstraints.gridx = mainWindowWidth * 4 / 5;
        commandButtonsPanelConstraints.gridy = mainWindowHeight - commandButtonsCount * commandButtonHeight;
        commandButtonsPanelConstraints.insets = new Insets(inset, inset, inset, inset);

        levelSelectionPanelConstraints = new GridBagConstraints();
        levelSelectionPanelConstraints.fill = GridBagConstraints.BOTH;
        levelSelectionPanelConstraints.weightx = 0.5;
        levelSelectionPanelConstraints.weighty = 0.5;
        levelSelectionPanelConstraints.gridwidth = levelSelectionPanelWidth;
        levelSelectionPanelConstraints.gridheight = levelSelectionPanelHeight;
        levelSelectionPanelConstraints.gridx = 0;
        levelSelectionPanelConstraints.gridy = 0;
        levelSelectionPanelConstraints.insets = new Insets(inset, inset, inset, inset);
    }

    private void setConstraintsForGamePanel() {
        int inset = 10;

        gamePanelConstraints = new GridBagConstraints();
        gamePanelConstraints.fill = GridBagConstraints.BOTH;
        gamePanelConstraints.weightx = 0.5;
        gamePanelConstraints.weighty = 0.5;
        gamePanelConstraints.gridwidth = gamePanelWidth;
        gamePanelConstraints.gridheight = gamePanelHeight;
        gamePanelConstraints.gridx = 0;
        gamePanelConstraints.gridy = 0;
        gamePanelConstraints.insets = new Insets(inset, inset, inset, inset);
    }

    private void setConstraintsForCountersPanel() {
        int inset = 10;

        countersPanelConstraints = new GridBagConstraints();
        countersPanelConstraints.fill = GridBagConstraints.BOTH;
        countersPanelConstraints.weightx = 0.5;
        countersPanelConstraints.weighty = 0;
        countersPanelConstraints.gridwidth = mainWindowWidth / 6;
        countersPanelConstraints.gridheight = (int) (1.5 * countersPanel.getIconsHeight());
        countersPanelConstraints.gridx = mainWindowWidth * 4 / 5;
        countersPanelConstraints.gridy = 0;
        countersPanelConstraints.insets = new Insets(inset, inset, inset, inset);
    }

    private void createGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rowsCount, columnsCount, 0, 0));
        gamePanel.setOpaque(false);

        int cellsCount = rowsCount * columnsCount;
        cells = new JButton[cellsCount];

        Font font = new Font("CourierNew", Font.BOLD, (int) (0.4 * cellSize));
        Color color = new Color(50, 50, 50);

        for (int i = 0; i < cellsCount; i++) {
            cells[i] = new Cell(cellSize);
            cells[i].setMargin(new Insets(0, 0, 0, 0));
            cells[i].addMouseListener(new NumberedMouseListener(i));
            cells[i].setFont(font);
            cells[i].setForeground(color);
            gamePanel.add(cells[i]);
        }
    }

    private void showGamePanel() {
        setGameParameters();
        setGamePanelGeometricalParameters();
        createIcons();
        setConstraintsForGamePanel();
        createGamePanel();
        mainPanel.remove(levelSelectionPanel);
        mainPanel.add(gamePanel, gamePanelConstraints);
        mainPanel.validate();
        mainPanel.repaint();
        isShownGamePanel = true;
        isFirstClick = true;
    }

    private void showCountersPanel() {
        String title = String.format("%s %d x %d", level, rowsCount, columnsCount);
        countersPanel = new CountersPanel(title, minedCellsCount);
        setConstraintsForCountersPanel();
        mainPanel.add(countersPanel, countersPanelConstraints);
        mainPanel.validate();
        mainPanel.repaint();
    }
}