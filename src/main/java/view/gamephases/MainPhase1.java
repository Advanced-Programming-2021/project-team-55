package view.gamephases;

import controller.gamephasescontrollers.GameController;
import controller.gamephasescontrollers.MainPhase1Controller;

public class MainPhase1 extends Duel {
    private static MainPhase1Controller mainPhase1Controller;


    @Override
    protected void execute(GameController gameController) {
        mainPhase1Controller=gameController.getMainPhase1Controller();

    }

    @Override
    protected void processCommand(String command) {

    }

}
