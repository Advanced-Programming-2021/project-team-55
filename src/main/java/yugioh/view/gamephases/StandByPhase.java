package yugioh.view.gamephases;

import yugioh.controller.gamephasescontrollers.StandByPhaseController;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.view.ViewInterface;

public class StandByPhase extends Duel {
    private StandByPhaseController standByPhaseController;


    @Override
    protected void execute() {
        standByPhaseController = gameController.getStandByPhaseController();
        String response = processCommand("");
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        standByPhaseController.activateEffects();
        gameController.changePhase();


        return response;
    }

}
