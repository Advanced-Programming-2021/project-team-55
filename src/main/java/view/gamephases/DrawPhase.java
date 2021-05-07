package view.gamephases;

import controller.gamephasescontrollers.DrawPhaseController;
import model.exceptions.GameException;
import view.ViewInterface;

public class DrawPhase extends Duel {
    private DrawPhaseController drawPhaseController;

    @Override
    protected void execute() {
        drawPhaseController = gameController.getDrawPhaseController();
        String response = processCommand("");
        ViewInterface.showResult(response);
        gameController.changePhase();
        showPhase();
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        try {
            response = drawPhaseController.removeFirstDeckCardFromDeckToPlay(gameController.getCurrentTurnPlayer());

        } catch (GameException e) {
            response = e.toString();
        }
        return response;
    }
}
