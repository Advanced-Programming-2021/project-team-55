package view.gamephases;

import controller.gamephasescontrollers.StandByPhaseController;
import view.ViewInterface;

public class StandByPhase extends Duel {
    private StandByPhaseController standByPhaseController;


    @Override
    protected void execute() {
        String response=processCommand("");
        ViewInterface.showResult(response);

    }

    @Override
    protected String processCommand(String command) {
        String response="";
        standByPhaseController=gameController.getStandByPhaseController();
        standByPhaseController.activateEffects();
        gameController.changePhase();
        showPhase(gameController);


        return response;
    }

}
