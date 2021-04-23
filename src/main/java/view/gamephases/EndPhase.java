package view.gamephases;

import controller.gamephasescontrollers.EndPhaseController;
import controller.gamephasescontrollers.GameController;

public class EndPhase extends Duel {
    private EndPhaseController endPhaseController;


    @Override
    protected void execute(GameController gameController) {
        endPhaseController=gameController.getEndPhaseController();
    }

    @Override
    protected void processCommand(String command) {

    }

}
