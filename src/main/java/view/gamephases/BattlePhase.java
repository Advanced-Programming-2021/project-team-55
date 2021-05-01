package view.gamephases;

import controller.gamephasescontrollers.BattlePhaseController;
import view.GameRegexes;
import view.ViewInterface;

public class BattlePhase extends Duel {
    private static BattlePhaseController battlePhaseController;

    @Override
    protected void execute() {
        battlePhaseController=gameController.getBattlePhaseController();
        String response=processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response="";
        if(command.matches(GameRegexes.NEXT_PHASE.regex)){
            gameController.changePhase();
            showPhase(gameController);
        }






        return response;
    }


}
