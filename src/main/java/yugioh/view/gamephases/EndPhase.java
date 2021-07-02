package yugioh.view.gamephases;

import yugioh.controller.gamephasescontrollers.EndPhaseController;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.view.ViewInterface;

public class EndPhase extends Duel {

    private EndPhaseController endPhaseController;

    @Override
    protected void execute() {
        GameMenuController.getGameMenuController().focusOpacityOnPhase(GamePhase.END);
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
