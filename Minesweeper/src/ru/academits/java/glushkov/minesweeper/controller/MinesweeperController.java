package ru.academits.java.glushkov.minesweeper.controller;

import ru.academits.java.glushkov.minesweeper.gui.View;
import ru.academits.java.glushkov.minesweeper.model.CellStatus;
import ru.academits.java.glushkov.minesweeper.model.Game;

import java.util.ArrayList;
import java.util.TreeSet;

public class MinesweeperController implements Controller {
    private final Game game;
    private View view;
    private int[] minedNeighborsCountsForCells;

    public MinesweeperController(Game game) {
        this.game = game;
    }

    @Override
    public void setGameParameters(int rowsCount, int columnsCount, int minedCellsCount) {
        game.setParameters(rowsCount, columnsCount, minedCellsCount);
        minedNeighborsCountsForCells = null;
    }

    @Override
    public String[][] getRecordData() {
        return game.getRecordData();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void updateOnLeftClick(int clickedCellNumber) {
        CellStatus cellStatus = game.getCellsStatusMap()[clickedCellNumber];

        if (cellStatus.equals(CellStatus.FLAGGED) || cellStatus.equals(CellStatus.QUESTIONED)) {
            return;
        }

        game.setTime(view.getTime());
        game.selectCell(clickedCellNumber);
        ArrayList<Integer> updatedCellsNumbers = game.getUpdatedCellsNumbers();

        if (minedNeighborsCountsForCells == null) {
            minedNeighborsCountsForCells = game.getMinedNeighborsCountsForCells();
        }

        for (Integer e : updatedCellsNumbers) {
            view.updateCell(e, String.valueOf(minedNeighborsCountsForCells[e]), game.getActiveMinesCount());
        }

        if (game.isOver()) {
            TreeSet<Integer> minedCellsSet = game.getMinedCellsSet();
            boolean isWin = game.isWin();

            for (Integer e : minedCellsSet) {
                view.showMinedCell(e, isWin);
            }

            view.disableField();
            view.showMessage(game.getMessage());
        }
    }

    @Override
    public void updateOnRightClick(int clickedCellNumber) {
        if (game.getCellsStatusMap()[clickedCellNumber].equals(CellStatus.OPENED)) {
            return;
        }

        game.markCell(clickedCellNumber);

        if (game.getCellsStatusMap()[clickedCellNumber].equals(CellStatus.UNMARKED)) {
            view.updateCell(clickedCellNumber, "", game.getActiveMinesCount());

            return;
        }

        view.updateCell(clickedCellNumber, game.getCellsStatusMap()[clickedCellNumber].toString(), game.getActiveMinesCount());
    }
}