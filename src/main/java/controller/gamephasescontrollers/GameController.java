package controller.gamephasescontrollers;

import controller.CheatController;
import model.board.Cell;
import model.Player;
import model.board.Game;
import view.gamephases.BattlePhase;
import view.gamephases.DrawPhase;

import java.util.ArrayList;

public class GameController {

    protected static CheatController cheatController;
    protected Player currentTurnPlayer;
    protected Player currentTurnOpponentPlayer;
    protected GamePhase currentPhase=GamePhase.DRAW;
    protected ArrayList<Cell> changedPositionCells;
    protected ArrayList<Cell> attackerCellsThisTurn;
    protected Game game;
    private DrawPhaseController drawPhaseController;
    private StandByPhaseController standByPhaseController;
    private MainPhase1Controller mainPhase1Controller;
    private BattlePhaseController battlePhaseController;
    private MainPhase2Controller mainPhase2Controller;
    private EndPhaseController endPhaseController;


    public GameController(Game game) {
        this.game=game;
        this.currentTurnPlayer= game.getFirstPlayer();
        this.currentTurnOpponentPlayer=game.getSecondPlayer();
        drawPhaseController=new DrawPhaseController();
        standByPhaseController=new StandByPhaseController();
        mainPhase1Controller=new MainPhase1Controller();
        battlePhaseController=new BattlePhaseController();
        mainPhase2Controller=new MainPhase2Controller();
        endPhaseController=new EndPhaseController();

    }

    public GameController() {
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

    public void changePhase() {
        switch (currentPhase){
            case DRAW:{
                currentPhase=GamePhase.STANDBY;
                break;
            }
            case STANDBY:{
                currentPhase=GamePhase.MAIN1;
                break;
            }
            case MAIN1:{
                currentPhase=GamePhase.BATTLE;
                break;
            }
            case BATTLE:{
                currentPhase=GamePhase.MAIN2;
                break;
            }
            case MAIN2:{
                currentPhase=GamePhase.END;
                break;
            }
            case END:{
                currentPhase=GamePhase.DRAW;
                changeTurn();
                break;
            }

        }
    }
    public void changeTurn(){
        Player player=currentTurnPlayer;
        currentTurnPlayer=currentTurnOpponentPlayer;
        currentTurnOpponentPlayer=currentTurnPlayer;
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

    public void setCurrentPhase(GamePhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public Player getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public Player getCurrentTurnOpponentPlayer(){
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
}
