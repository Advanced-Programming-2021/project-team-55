package view.gamephases;

import controller.gamephasescontrollers.StandByPhaseController;
import view.ViewInterface;

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
