package yugioh.view.gamephases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import yugioh.controller.gamephasescontrollers.DrawPhaseController;
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

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(7), event -> gameController.changePhase()));
        timeline.play();

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
