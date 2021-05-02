package view.gamephases;

import controller.gamephasescontrollers.MainPhase2Controller;
import exceptions.GameException;
import view.GameRegexes;
import view.ViewInterface;

public class MainPhase2 extends Duel {
    private MainPhase2Controller mainPhase2Controller;


    @Override
    protected void execute() {
        mainPhase2Controller = gameController.getMainPhase2Controller();
        ViewInterface.showResult(mainPhase2Controller.showGameBoard(gameController.currentTurnPlayer,
                gameController.currentTurnOpponentPlayer));
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);

    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches(GameRegexes.NEXT_PHASE.regex)) {
            gameController.changePhase();
            showPhase(gameController);
        } else if (command.matches(GameRegexes.SELECT.regex)) {
            response = processSelect(command);
        } else if (command.matches(GameRegexes.DESELECT.regex)) {
            try {
                gameController.deselect();
                response = GameResponses.CARD_DESELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else {
            response = GameResponses.INVALID_COMMAND.response;
        }
        return response;
    }

}
