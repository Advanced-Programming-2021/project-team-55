package view.gamephases;

import controller.gamephasescontrollers.GameController;
import view.ViewInterface;

abstract public class Duel {

    private static DrawPhase drawPhase = new DrawPhase();
    private static MainPhase1 mainPhase1 = new MainPhase1();
    private static BattlePhase battlePhase = new BattlePhase();
    private static StandByPhase standByPhase = new StandByPhase();
    private static MainPhase2 mainPhase2 = new MainPhase2();
    private static EndPhase endPhase = new EndPhase();
    public static GameController gameController;

    public static void runGame(GameController gameController) {
        Duel.gameController=gameController;
        showPhase(gameController);
        while (true) {
            switch (gameController.getCurrentPhase()) {
                case DRAW: {
                    drawPhase.execute();
                    break;
                }
                case STANDBY: {
                    standByPhase.execute();
                    break;
                }
                case MAIN1: {
                    mainPhase1.execute();
                    break;
                }
                case MAIN2: {
                    mainPhase2.execute();
                    break;
                }
                case BATTLE: {
                    battlePhase.execute();
                    break;
                }
                case END: {
                    endPhase.execute();
                    break;
                }
            }
        }
    }

    abstract protected void execute();
    abstract protected String  processCommand(String command);
    protected static void showPhase(GameController gameController){
        ViewInterface.showResult("phase: "+gameController.getCurrentPhase().name);
    }
}
