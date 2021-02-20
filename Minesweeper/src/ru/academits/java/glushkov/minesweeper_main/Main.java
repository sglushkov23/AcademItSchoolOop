package ru.academits.java.glushkov.minesweeper_main;

import ru.academits.java.glushkov.minesweeper.controller.Controller;
import ru.academits.java.glushkov.minesweeper.controller.MinesweeperController;
import ru.academits.java.glushkov.minesweeper.gui.DesktopView;
import ru.academits.java.glushkov.minesweeper.gui.View;
import ru.academits.java.glushkov.minesweeper.model.Game;
import ru.academits.java.glushkov.minesweeper.model.MinesweeperGame;

public class Main {
    public static void main(String[] args) {
        Game game  = new MinesweeperGame();
        Controller controller = new MinesweeperController(game);
        View view = new DesktopView(controller);

        controller.setView(view);
        view.start();
    }
}