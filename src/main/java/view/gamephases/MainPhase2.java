package view.gamephases;

import controller.gamephasescontrollers.GameController;
import controller.gamephasescontrollers.MainPhase2Controller;

public class MainPhase2 extends Duel {
    private MainPhase2Controller mainPhase2Controller;


    @Override
    protected void execute(GameController gameController) {
        mainPhase2Controller=gameController.getMainPhase2Controller();

    }

    @Override
    protected void processCommand(String command) {

    }

}
