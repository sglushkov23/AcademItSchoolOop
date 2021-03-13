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
    private Cell[] cells;
    private JFrame frame;
    private int mainWindowWidth;
    private int mainWindowHeight;
    private int levelSelectionPanelWidth;
    private int levelSelectionPanelHeight;
    private int startCellSize;
    private int currentCellSize;
    private int minCellSize;
    private int maxCellSize;
    private Font cellFont;
    private ImageIcon flagIcon;
    private ImageIcon questionIcon;
    private ImageIcon mineIcon;
    private ImageIcon explosionIcon;
    private Image flagImage;
    private Image questionImage;
    private Image mineImage;
    private Image explosionImage;
    private boolean isShownGamePanel;
    private boolean isFirstClick;
    private JPanel mainPanel;
    private JPanel levelSelectionPanel;
    private JPanel gamePanel;
    private JPanel gamePanelContainer;
    private JPanel countersPanelContainer;
    private CustomLevelSelectionPanel customLevelSelectionPanel;
    private CountersPanel countersPanel;
    private Thread stopwatchThread;

    public DesktopView(Controller controller) {
        this.controller = controller;
    }

    private class NumberedMouseListener extends MouseAdapter {
        private final int number;

        public NumberedMouseListener(int number) {
            this.number = number;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (!isAllowedToListen) {
                return;
            }

            if (e.getButton() == MouseEvent.BUTTON2) {
                return;
            }

            if (e.getButton() == MouseEvent.BUTTON1) {
                controller.updateOnLeftClick(number);
            } else {
                controller.updateOnRightClick(number);
            }

            if (isFirstClick) {
                stopwatchThread = new Thread(new Stopwatch(countersPanel));
                stopwatchThread.start();
                isFirstClick = false;
            }
        }
    }

    @Override
    public void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                UIManager.put("Button.disabledText", new Color(50, 50, 50));
            } catch (Exception ignored) {
            }

            gamePanelContainer = new JPanel();
            gamePanelContainer.setLayout(new GridBagLayout());
            gamePanelContainer.setOpaque(true);
            gamePanelContainer.setBackground(new Color(100, 100, 100));

            countersPanelContainer = new JPanel();
            countersPanelContainer.setOpaque(true);
            countersPanelContainer.setBackground(new Color(150, 150, 150));

            setMainWindowGeometricalParameters();

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
                        customLevelSelectionPanel.getPanel(),
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

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("Menu");
            menu.setMnemonic(KeyEvent.VK_M);
            menuBar.add(menu);

            JMenuItem newGameItem = new JMenuItem("New Game", KeyEvent.VK_N);
            newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

            JMenuItem highScoresItem = new JMenuItem("High Scores", KeyEvent.VK_H);
            highScoresItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));

            JMenuItem aboutItem = new JMenuItem("About", KeyEvent.VK_A);
            aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

            JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
            exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

            newGameItem.addActionListener(e -> {
                if (isShownGamePanel) {
                    mainPanel.remove(gamePanelContainer);
                    mainPanel.remove(countersPanelContainer);
                    gamePanelContainer.remove(gamePanel);
                    countersPanelContainer.remove(countersPanel);
                    mainPanel.add(levelSelectionPanel);
                    mainPanel.validate();
                    mainPanel.repaint();
                    isShownGamePanel = false;
                    isAllowedToListen = true;
                }
            });

            highScoresItem.addActionListener(e -> {
                HighScoresPanel highScoresPanel = new HighScoresPanel(controller.getRecordData());

                JOptionPane.showOptionDialog(
                        frame,
                        highScoresPanel.getPanel(),
                        "High Scores",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        null,
                        null
                );
            });

            aboutItem.addActionListener(e -> {
                String message = String.format("Minesweeper version 1.0.1%nBuilt on March 12, 2021%nAuthor: Glushkov Sergey");
                JOptionPane.showMessageDialog(frame, message);
            });

            exitItem.addActionListener(e -> System.exit(0));

            menu.add(newGameItem);
            menu.addSeparator();
            menu.add(highScoresItem);
            menu.addSeparator();
            menu.add(aboutItem);
            menu.addSeparator();
            menu.add(exitItem);

            mainPanel.setBackground(new Color(125, 125, 125));
            mainPanel.add(levelSelectionPanel);

            frame = new JFrame("Minesweeper");
            frame.setSize(mainWindowWidth, mainWindowHeight);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setContentPane(mainPanel);
            frame.setJMenuBar(menuBar);
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
            } else {
                cells[cellNumber].setText(data);
            }

            cells[cellNumber].setIcon(null);

            if (!data.equals("")) {
                cells[cellNumber].setEnabled(false);
            }
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
            cells[cellNumber].setDisabledIcon(mineIcon);
        } else {
            cells[cellNumber].setIcon(explosionIcon);
            cells[cellNumber].setDisabledIcon(explosionIcon);
        }

        cells[cellNumber].setEnabled(false);
    }

    @Override
    public void disableField() {
        stopwatchThread.interrupt();
        isAllowedToListen = false;

        for (Cell cell : cells) {
            cell.setEnabled(false);
        }
    }

    @Override
    public String getTime() {
        return countersPanel.getCurrentTime();
    }

    private void createIcons() {
        double resizeCoefficient = 0.8;
        int iconSize = (int) (resizeCoefficient * currentCellSize);
        //String path = "..\\resources\\";
        String path = "";

        flagImage = IconsCreator.createImage(path + "flag.png", "flag");
        questionImage = IconsCreator.createImage(path + "question_mark.png", "question");
        mineImage = IconsCreator.createImage(path + "mine.png", "mine");
        explosionImage = IconsCreator.createImage(path + "explosion.png", "exploded_mine");

        flagIcon = IconsCreator.createImageIcon(flagImage, iconSize);
        questionIcon = IconsCreator.createImageIcon(questionImage, iconSize);
        mineIcon = IconsCreator.createImageIcon(mineImage, iconSize);
        explosionIcon = IconsCreator.createImageIcon(explosionImage, iconSize);
    }

    private void resizeIcons() {
        double resizeCoefficient = 0.8;
        int iconSize = (int) (resizeCoefficient * currentCellSize);

        IconsCreator.setResizedImage(flagIcon, flagImage, iconSize);
        IconsCreator.setResizedImage(questionIcon, questionImage, iconSize);
        IconsCreator.setResizedImage(mineIcon, mineImage, iconSize);
        IconsCreator.setResizedImage(explosionIcon, explosionImage, iconSize);
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

        gamePanelContainer.setPreferredSize(new Dimension((int) (0.98 * mainWindowWidth * 4 / 5), (int) (0.9 * mainWindowHeight)));
        countersPanelContainer.setPreferredSize(new Dimension((int) (0.98 * mainWindowWidth / 5), (int) (0.9 * mainWindowHeight)));

        levelSelectionPanelWidth = (int) (0.95 * mainWindowWidth * 4 / 5);
        levelSelectionPanelHeight = levelSelectionPanelWidth / 4 - 1;
    }

    private void setCellSizes() {
        int maxRowsCount = 24;
        int maxColumnsCount = 30;

        startCellSize = Math.min(mainWindowHeight / maxRowsCount - 1, mainWindowWidth * 4 / 5 / maxColumnsCount - 1);
        currentCellSize = startCellSize;
        minCellSize = (int) (0.95 * startCellSize);
        maxCellSize = Math.min(
                (int) gamePanelContainer.getPreferredSize().getWidth() / columnsCount + 1,
                (int) gamePanelContainer.getPreferredSize().getHeight() / rowsCount + 1
        );
    }

    private void createGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rowsCount, columnsCount, 0, 0));
        gamePanel.setOpaque(false);

        int cellsCount = rowsCount * columnsCount;
        cells = new Cell[cellsCount];

        cellFont = new Font("CourierNew", Font.BOLD, (int) (0.4 * startCellSize));
        Color color = new Color(50, 50, 50);

        for (int i = 0; i < cellsCount; i++) {
            cells[i] = new Cell(startCellSize);
            cells[i].setMargin(new Insets(0, 0, 0, 0));
            cells[i].addMouseListener(new NumberedMouseListener(i));
            cells[i].setFont(cellFont);
            cells[i].setForeground(color);
            gamePanel.add(cells[i]);
        }
    }

    private void showGamePanel() {
        setGameParameters();
        setCellSizes();
        createIcons();
        createGamePanel();
        mainPanel.remove(levelSelectionPanel);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        mainPanel.add(gamePanelContainer, c);
        mainPanel.add(countersPanelContainer, c);
        gamePanelContainer.add(gamePanel);

        gamePanel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                double enlargementCoefficient = 1.05;

                if (rotation < 0 && currentCellSize * enlargementCoefficient <= maxCellSize) {
                    currentCellSize *= enlargementCoefficient;
                } else if (rotation > 0 && currentCellSize / enlargementCoefficient >= minCellSize) {
                    currentCellSize /= enlargementCoefficient;
                } else {
                    if (currentCellSize * enlargementCoefficient > maxCellSize) {
                        currentCellSize = maxCellSize;
                    } else if (currentCellSize / enlargementCoefficient < minCellSize) {
                        currentCellSize = minCellSize;
                    }
                }

                resizeGamePanel();
            }
        });

        mainPanel.validate();
        mainPanel.repaint();
        isShownGamePanel = true;
        isFirstClick = true;
    }

    private void resizeGamePanel() {
        float fontSize = (int) (0.4 * currentCellSize);
        cellFont = cellFont.deriveFont(fontSize);
        resizeIcons();

        for (Cell cell : cells) {
            cell.setSize(currentCellSize);
            cell.setFont(cellFont);
        }

        gamePanelContainer.validate();
        gamePanelContainer.repaint();
    }

    private void showCountersPanel() {
        String title = String.format("%s %d x %d", level, rowsCount, columnsCount);
        countersPanel = new CountersPanel(title, minedCellsCount);
        countersPanelContainer.add(countersPanel);
        mainPanel.validate();
        mainPanel.repaint();
    }
}