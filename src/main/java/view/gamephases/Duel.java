package view.gamephases;

import controller.gamephasescontrollers.DrawPhaseController;
import controller.gamephasescontrollers.GameController;
import controller.gamephasescontrollers.GamePhase;
import view.ViewInterface;

abstract public class Duel {

    private static DrawPhase drawPhase = new DrawPhase();
    private static MainPhase1 mainPhase1 = new MainPhase1();
    private static BattlePhase battlePhase = new BattlePhase();
    private static StandByPhase standByPhase = new StandByPhase();
    private static MainPhase2 mainPhase2 = new MainPhase2();
    private static EndPhase endPhase = new EndPhase();

    public static void runGame(GameController gameController) {
        showPhase(gameController);
        while (true) {
            switch (gameController.getCurrentPhase()) {
                case DRAW: {
                    drawPhase.execute(gameController);
                    break;
                }
                case STANDBY: {
                    standByPhase.execute(gameController);
                    break;
                }
                case MAIN1: {
                    mainPhase1.execute(gameController);
                    break;
                }
                case MAIN2: {
                    mainPhase2.execute(gameController);
                    break;
                }
                case BATTLE: {
                    battlePhase.execute(gameController);
                    break;
                }
                case END: {
                    endPhase.execute(gameController);
                    break;
                }
            }
        }
    }

    abstract protected void execute(GameController gameController);
    abstract protected void processCommand(String command);
    protected static void showPhase(GameController gameController){
        ViewInterface.showResult(gameController.getCurrentPhase().name);
    }
}
