package controller.gamephasescontrollers;

import controller.CheatController;
import exceptions.GameException;
import model.Player;
import model.User;
import model.board.Cell;
import model.board.Game;
import model.board.GameBoard;
import view.GameRegexes;
import view.ViewInterface;
import view.gamephases.Duel;
import view.gamephases.GamePhase;
import view.gamephases.GameResponses;

import java.util.ArrayList;

public class GameController {

    public static CheatController cheatController;
    public Player currentTurnPlayer;
    public Player currentTurnOpponentPlayer;
    public GamePhase currentPhase ;
    public ArrayList<Cell> changedPositionCells ;
    public ArrayList<GamePhase> phases ;
    public ArrayList<Cell> attackerCellsThisTurn;
    protected Game game;
    private int currentRound = 1;
    private boolean didPlayerSetOrSummonThisTurn = false;
    private DrawPhaseController drawPhaseController;
    private StandByPhaseController standByPhaseController;
    private MainPhase1Controller mainPhase1Controller;
    private BattlePhaseController battlePhaseController;
    private MainPhase2Controller mainPhase2Controller;
    private EndPhaseController endPhaseController;

    private void gameControllerInitialization(){
        currentPhase=GamePhase.DRAW;
        changedPositionCells=new ArrayList<>();
        phases=new ArrayList<>();
        didPlayerSetOrSummonThisTurn=false;
        this.currentTurnPlayer = game.getFirstPlayer();
        this.currentTurnOpponentPlayer = game.getSecondPlayer();
        drawPhaseController = new DrawPhaseController(this);
        standByPhaseController = new StandByPhaseController(this);
        mainPhase1Controller = new MainPhase1Controller(this);
        battlePhaseController = new BattlePhaseController(this);
        mainPhase2Controller = new MainPhase2Controller(this);
        endPhaseController = new EndPhaseController(this);
        currentTurnPlayer.resetGameBoard();
        currentTurnOpponentPlayer.resetGameBoard();
    }
    public GameController(Game game) {
        this.game = game;
        gameControllerInitialization();
    }

    public GameController() {
    }

    public ArrayList<Cell> getChangedPositionCells() {
        return changedPositionCells;
    }

    public String showGraveyard() throws GameException {
        String response = "";
        GameBoard playerGameBoard = currentTurnPlayer.getGameBoard();
        if (playerGameBoard.getGraveyard().size() == 0) {
            throw new GameException(GameResponses.GRAVEYARD_EMPTY.response);
        } else {
            for (int i = 1; i <= playerGameBoard.getGraveyard().size(); i++) {
                response += i + ". " + playerGameBoard.getGraveyard().get(i - 1).getCellCard();
            }
        }
        return response;
    }

    public String showCard() throws GameException {
        if (Cell.getSelectedCell() == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        } else if (!currentTurnOpponentPlayer.getGameBoard().isCellVisibleToOpponent(Cell.getSelectedCell())) {
            throw new GameException(GameResponses.CARD_IS_NOT_VISIBLE.response);
        } else {
            return Cell.getSelectedCell().getCellCard().toString();
        }
    }


    protected String showPhase() {
        return null;
    }

    //todo : should we deselect automatically when a command is done or not?
    public void selectCard(String zone, int number, boolean opponent) throws GameException {
        GameBoard currentPlayerGameBoard = currentTurnPlayer.getGameBoard();
        GameBoard opponentPlayerGameBoard = currentTurnOpponentPlayer.getGameBoard();
        Cell selectedCell = null;
        number -= 1;
        int[] areasNumber = GameBoard.areasNumber;
        switch (zone) {
            case "monster": {
                if (number > 4) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);
                } else {
                    if (opponent) {
                        selectedCell = opponentPlayerGameBoard.getMonsterCardZone()[areasNumber[number]];
                    } else {
                        selectedCell = currentPlayerGameBoard.getMonsterCardZone()[areasNumber[number]];
                    }
                }
                break;
            }
            case "spell": {
                if (number > 4) {
                    throw new GameException(GameResponses.INVALID_SELECTION.response);
                }
                if (opponent) {
                    selectedCell = opponentPlayerGameBoard.getSpellAndTrapCardZone()[areasNumber[number]];
                } else {
                    selectedCell = currentPlayerGameBoard.getSpellAndTrapCardZone()[areasNumber[number]];
                }
                break;
            }
            case "field": {
                if (opponent) {
                    selectedCell = opponentPlayerGameBoard.getFieldZone();
                } else {
                    selectedCell = currentPlayerGameBoard.getFieldZone();
                }
                break;
            }
            case "hand": {
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
        if (Cell.getSelectedCell() == null) {
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
        changedPositionCells = new ArrayList<>();
        //todo update changedPositionCells & other fields
        //todo reset attacked arraylist
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

    public void surrender() {
        endGameRound(currentTurnOpponentPlayer,currentTurnPlayer);
    }

    protected void checkGameWinner() {

    }
    public void endDuel(){
        currentRound=game.getRounds();
        if(game.getRounds()==3) {
            game.setPlayerScore(currentTurnPlayer, 2000);
        }
        endGameRound(currentTurnPlayer,currentTurnOpponentPlayer);
    }
    public void endGameRound(Player winner,Player loser) {
        game.addWinner(winner);
        game.addLoser(loser);
        String response="";
        response = calculateScoresAndMoney(winner, loser);
        if (game.getRounds() == currentRound) {
            Duel.setGameIsEnded(true);
            ViewInterface.showResult(response);
        }
        else{
            gameControllerInitialization();
            currentRound++;
            ViewInterface.showResult(response);
            Duel.runGame(this);
        }

    }

    private String calculateScoresAndMoney(Player winner, Player loser) {
        User winnerUser = winner.getUser();
        User loserUser = loser.getUser();
        String response = winnerUser.getUsername() + " won the game and the score is: ";
        game.increasePlayerScore(winner, 1000);
        game.setPlayersLP(winner, winner.getLP());
        game.setPlayersLP(loser, loser.getLP());
        response += game.getPlayerScore(winner) + "-" + game.getPlayerScore(loser);
        if (game.getRounds() == currentRound) {
            winnerUser.changeScore(game.getPlayerScore(winner));
            if (currentRound == 1) {
                winnerUser.changeMoney(1000 + winner.getLP());
                loserUser.changeMoney(100);
            } else {
                winnerUser.changeMoney(3000 + 3 * game.getPlayersMaxLP(winner));
                loserUser.changeMoney(300);
            }
            response += "\n" + game.getWinner().getUser().getUsername() + " won the whole match with score: ";
            response += game.getPlayerScore(winner) + "-" + game.getPlayerScore(loser);
        }
        return response;
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

    public boolean didCardAttackedThisTurn(Cell cell) {
        return attackerCellsThisTurn.contains(cell);
    }

    public boolean canPlayerDirectAttack(Cell cell) {
        if (currentTurnOpponentPlayer.getGameBoard().isMonsterCardZoneEmpty()) return false;
        return true;//todo طبق داک ممکنه دلایل دیگه هم وجود داشته باشه
    }

}
