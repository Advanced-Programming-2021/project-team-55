package view.gamephases;

import controller.gamephasescontrollers.MainPhase2Controller;
import view.GameRegexes;
import view.ViewInterface;

public class MainPhase2 extends Duel {
    private MainPhase2Controller mainPhase2Controller;


    @Override
    protected void execute() {
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
