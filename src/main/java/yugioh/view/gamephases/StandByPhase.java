package yugioh.view.gamephases;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import yugioh.controller.gamephasescontrollers.StandByPhaseController;
import yugioh.view.ViewInterface;

public class StandByPhase extends Duel {
    private StandByPhaseController standByPhaseController;


    @Override
    protected void execute() {
        standByPhaseController = gameController.getStandByPhaseController();
        String response = processCommand("");
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        standByPhaseController.activateEffects();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> gameController.changePhase()));//todo make duration 3.5
        timeline.play();

        return response;
    }

}
