package controller.gamephasescontrollers;

import controller.CheatController;
import model.Player;
import model.board.Cell;
import model.board.Game;

import java.util.ArrayList;

public class GameController {

    protected static CheatController cheatController;
    protected Player currentTurnPlayer;
    protected Player currentTurnOpponentPlayer;
    protected GamePhase currentPhase = GamePhase.DRAW;
    protected ArrayList<Cell> changedPositionCells;
    protected ArrayList<Cell> attackerCellsThisTurn;
    protected Game game;

    public GameController(Game game) {
        this.game = game;
    }

    protected String showGameBoards() {
        return null;
    }

    protected String showPhase() {
        return null;
    }

    protected void selectCard(String zone, int number) {

    }

    protected void deselect() {

    }

    protected void changePhase() {

    }

    protected String showGraveyard(Player player) {
        return null;
    }

    protected void handleCardSideEffects() {

    }

    protected boolean isCellSelected(Cell cell) {
        return false;
    }

    protected boolean canCardBeActivated(Cell cell) {
        return false;
    }

    protected void nonMonsterActivate(Cell cell) {

    }

    protected void surrender() {

    }

    protected void checkGameWinner() {

    }

    public GamePhase getCurrentPhase() {
        return null;
    }

}
