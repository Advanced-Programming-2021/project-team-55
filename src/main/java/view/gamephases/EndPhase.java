package view.gamephases;

import controller.gamephasescontrollers.EndPhaseController;
import view.ViewInterface;

public class EndPhase extends Duel {
    private EndPhaseController endPhaseController;


    @Override
    protected void execute() {
        String response=processCommand("");
        ViewInterface.showResult(response);
        showPhase(gameController);
    }

    @Override
    protected String processCommand(String command) {
        String response="its "+gameController.getCurrentTurnOpponentPlayer().getUser().getNickname()+"’s turn";
        gameController.changeTurn();
        gameController.changePhase();

        return response;
    }

}