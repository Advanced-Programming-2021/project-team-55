package yugioh.view.gamephases;

import yugioh.controller.gamephasescontrollers.DrawPhaseController;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.model.cards.trapandspells.TimeSeal;
import yugioh.model.exceptions.GameException;
import yugioh.view.ViewInterface;

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
            if (TimeSeal.handleEffect(gameController, getGameController().getCurrentTurnPlayer())) return "";
            response = drawPhaseController.removeFirstDeckCardFromDeckToPlay(gameController.getCurrentTurnPlayer());

        } catch (GameException e) {
            response = e.toString();
        }
        return response;
    }
}
