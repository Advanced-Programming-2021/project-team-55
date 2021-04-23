package view.gamephases;

import controller.gamephasescontrollers.MainPhase1Controller;
import view.ViewInterface;

public class MainPhase1 extends Duel {
    private static MainPhase1Controller mainPhase1Controller;


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
