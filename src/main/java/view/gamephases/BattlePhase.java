package view.gamephases;

import controller.gamephasescontrollers.BattlePhaseController;
import controller.gamephasescontrollers.GameController;

public class BattlePhase extends Duel {
    private static BattlePhaseController battlePhaseController;

    @Override
    protected void execute(GameController gameController) {
        battlePhaseController=gameController.getBattlePhaseController();

    }

    @Override
    protected void processCommand(String command) {

    }


}
