package view.gamephases;

import controller.gamephasescontrollers.DrawPhaseController;
import model.cards.trapandspells.TimeSeal;
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
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        try {
            if (TimeSeal.handleEffect(gameController, Duel.getGameController().getCurrentTurnPlayer())) return "";
            response = drawPhaseController.removeFirstDeckCardFromDeckToPlay(gameController.getCurrentTurnPlayer());

        } catch (GameException e) {
            response = e.toString();
        }
        return response;
    }
}
