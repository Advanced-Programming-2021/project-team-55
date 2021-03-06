package view.gamephases;

import controller.gamephasescontrollers.EndPhaseController;
import view.ViewInterface;

public class EndPhase extends Duel {

    private EndPhaseController endPhaseController;

    @Override
    protected void execute() {
        endPhaseController = gameController.getEndPhaseController();
        String response = processCommand("");
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (Duel.getGameController().getCurrentTurnPlayer().isAI()) response = "AI turn completed";
        endPhaseController.handleCardsSideEffectsForThisPhase();
        gameController.changePhase();
        return response;
    }

}
