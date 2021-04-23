package view.gamephases;

import controller.gamephasescontrollers.GameController;
import controller.gamephasescontrollers.StandByPhaseController;

public class StandByPhase extends Duel {
    private StandByPhaseController standByPhaseController;


    @Override
    protected void execute(GameController gameController) {
        standByPhaseController=gameController.getStandByPhaseController();
        standByPhaseController.activateEffects();

    }

    @Override
    protected void processCommand(String command) {

    }

}
