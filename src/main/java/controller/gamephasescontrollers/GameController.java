package controller.gamephasescontrollers;

import controller.CheatController;
import exceptions.GameException;
import model.Player;
import model.board.Cell;
import model.board.Game;
import model.board.GameBoard;
import view.GameRegexes;
import view.gamephases.GameResponses;

import java.util.ArrayList;

public class GameController {

    public static CheatController cheatController;
    public Player currentTurnPlayer;
    public Player currentTurnOpponentPlayer;
    public GamePhase currentPhase = GamePhase.DRAW;
    public ArrayList<Cell> changedPositionCells;
    public ArrayList<Cell> attackerCellsThisTurn;
    private boolean didPlayerSetOrSummonThisTurn = false;
    protected Game game;
    private DrawPhaseController drawPhaseController;
    private StandByPhaseController standByPhaseController;
    private MainPhase1Controller mainPhase1Controller;
    private BattlePhaseController battlePhaseController;
    private MainPhase2Controller mainPhase2Controller;
    private EndPhaseController endPhaseController;


    public GameController(Game game) {
        this.game = game;
        this.currentTurnPlayer = game.getFirstPlayer();
        this.currentTurnOpponentPlayer = game.getSecondPlayer();
        drawPhaseController = new DrawPhaseController(this);
        standByPhaseController = new StandByPhaseController(this);
        mainPhase1Controller = new MainPhase1Controller(this);
        battlePhaseController = new BattlePhaseController(this);
        mainPhase2Controller = new MainPhase2Controller(this);
        endPhaseController = new EndPhaseController(this);
    }

    public GameController() {
    }

    protected String showGameBoards() {
        return null;
    }

    protected String showPhase() {
        return null;
    }

    public void selectCard(String zone, int number, boolean opponent) throws
            GameException {
        GameBoard currentPlayerGameBoard = currentTurnPlayer.getGameBoard();
        GameBoard opponentPlayerGameBoard = currentTurnOpponentPlayer.getGameBoard();
        Cell selectedCell = null;
        number -= 1;
        switch (zone) {
            case "Monster": {
                if (number > 4) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);
                } else {
                    if (opponent) {
                        selectedCell = opponentPlayerGameBoard.getMonsterCardZone()[number];
                    } else {
                        selectedCell = currentPlayerGameBoard.getMonsterCardZone()[number];
                    }
                }
                break;
            }
            case "Spell": {
                if (number > 4) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);
                }
                if (opponent) {
                    selectedCell = opponentPlayerGameBoard.getSpellAndTrapCardZone()[number];
                } else {
                    selectedCell = currentPlayerGameBoard.getSpellAndTrapCardZone()[number];
                }
                break;
            }
            case "Field": {
                if (opponent) {
                    selectedCell = opponentPlayerGameBoard.getFieldZone();
                } else {
                    selectedCell = currentPlayerGameBoard.getFieldZone();
                }
                break;
            }
            case "Hand": {
                if (number >= currentPlayerGameBoard.getHandCards().size()) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);

                }
                selectedCell = currentPlayerGameBoard.getHandCards().get(number);
                break;
            }
        }
        if (selectedCell.getCellCard() == null) {
            throw new GameException(GameResponses.NO_CARDS_FOUND.response);
        } else {
            Cell.setSelectedCell(selectedCell);
        }

    }


    public void deselect() throws GameException {
        if (Cell.getSelectedCell() != null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        Cell.setSelectedCell(null);
    }

    public void setCurrentTurnPlayer(Player currentTurnPlayer) {
        this.currentTurnPlayer = currentTurnPlayer;
    }

    public void setCurrentTurnOpponentPlayer(Player currentTurnOpponentPlayer) {
        this.currentTurnOpponentPlayer = currentTurnOpponentPlayer;
    }

    public void changePhase() {
        switch (currentPhase) {
            case DRAW: {
                currentPhase = GamePhase.STANDBY;
                break;
            }
            case STANDBY: {
                currentPhase = GamePhase.MAIN1;
                break;
            }
            case MAIN1: {
                currentPhase = GamePhase.BATTLE;
                break;
            }
            case BATTLE: {
                currentPhase = GamePhase.MAIN2;
                break;
            }
            case MAIN2: {
                currentPhase = GamePhase.END;
                break;
            }
            case END: {
                currentPhase = GamePhase.DRAW;
                changeTurn();
                break;
            }

        }
    }

    private void changeTurn() {
        Player player = currentTurnPlayer;
        currentTurnPlayer = currentTurnOpponentPlayer;
        currentTurnOpponentPlayer = player;
        didPlayerSetOrSummonThisTurn = false;
        //todo update changedPositionCells & other fields
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

    public boolean DoPlayerSetOrSummonedThisTurn() {
        return didPlayerSetOrSummonThisTurn;
    }

    public void setDidPlayerSetOrSummonThisTurn(boolean didPlayerSetOrSummonThisTurn) {
        this.didPlayerSetOrSummonThisTurn = didPlayerSetOrSummonThisTurn;
    }

    protected void nonMonsterActivate(Cell cell) {

    }

    protected void surrender() {

    }

    protected void checkGameWinner() {

    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(GamePhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Player getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public Player getCurrentTurnOpponentPlayer() {
        return currentTurnOpponentPlayer;
    }

    public DrawPhaseController getDrawPhaseController() {
        return drawPhaseController;
    }

    public EndPhaseController getEndPhaseController() {
        return endPhaseController;
    }

    public MainPhase1Controller getMainPhase1Controller() {
        return mainPhase1Controller;
    }

    public MainPhase2Controller getMainPhase2Controller() {
        return mainPhase2Controller;
    }

    public StandByPhaseController getStandByPhaseController() {
        return standByPhaseController;
    }

    public BattlePhaseController getBattlePhaseController() {
        return battlePhaseController;
    }

    public boolean checkCommandIsInCurrentPhase(String command) {
        if (command.matches(GameRegexes.SUMMON.regex) || command.matches(GameRegexes.SET.regex) ||
                command.matches(GameRegexes.SET_POSITION.regex) || command.matches(GameRegexes.FLIP_SUMMON.regex) ||
                command.matches(GameRegexes.ACTIVATE_EFFECT.regex)) {
            return currentPhase == GamePhase.MAIN1 || currentPhase == GamePhase.MAIN2;
        } else if (command.matches(GameRegexes.ATTACK.regex) || command.matches(GameRegexes.ATTACK_DIRECT.regex)) {
            return currentPhase == GamePhase.BATTLE;
        }
        return true;
    }


}
