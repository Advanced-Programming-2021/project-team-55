package yugioh.view.gamephases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import yugioh.controller.gamephasescontrollers.EndPhaseController;
import yugioh.view.ViewInterface;

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
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3.5), event -> gameController.changePhase()));
        timeline.play();
        return response;
    }

}
