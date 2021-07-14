package yugioh.client.view.gamephases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import yugioh.client.controller.gamephasescontrollers.StandByPhaseController;
import yugioh.client.view.ViewInterface;

public class StandByPhase extends Duel {
    private StandByPhaseController standByPhaseController;


    @Override
    protected void execute() {
        standByPhaseController = gameController.getStandByPhaseController();
        String response = processCommand("");
//        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        standByPhaseController.activateEffects();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3.5), event -> gameController.changePhase()));
        timeline.play();

        return response;
    }

}
