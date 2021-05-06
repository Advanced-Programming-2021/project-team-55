package view.gamephases;

import controller.gamephasescontrollers.DrawPhaseController;
import exceptions.GameException;
import view.ViewInterface;

public class DrawPhase extends Duel {
    private DrawPhaseController drawPhaseController;

    @Override
    protected void execute() {
        drawPhaseController = gameController.getDrawPhaseController();
        String response = processCommand("");
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        try {
            response = drawPhaseController.removeFirstDeckCardFromDeckToPlay(gameController.getCurrentTurnPlayer());
            gameController.changePhase();
            showPhase(gameController);
        } catch (GameException e) {
            response = e.toString();
        }

        return response;
    }
}
