package view.gamephases;

import view.ViewInterface;

public class Graveyard extends Duel {

    private static GamePhase lastPhase;

    @Override
    protected void execute() {
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.matches("back")) {
//            gameController.currentPhase = gameController.phases.get(gameController.phases.size() - 2);
            gameController.currentPhase = lastPhase;
        } else {
            response = GameResponses.INVALID_COMMAND.response;
        }
        return response;
    }

    public static void setLastPhase(GamePhase lastPhase) {
        Graveyard.lastPhase = lastPhase;
    }
}
