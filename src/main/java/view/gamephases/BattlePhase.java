package view.gamephases;

import controller.gamephasescontrollers.BattlePhaseController;
import controller.gamephasescontrollers.GameController;
import exceptions.GameException;
import model.board.Cell;
import view.GameRegexes;
import view.Regexes;
import view.ViewInterface;

import java.util.regex.Matcher;

public class BattlePhase extends Duel {
    private static BattlePhaseController battlePhaseController;

    @Override
    protected void execute() {
        /*battlePhaseController = gameController.getBattlePhaseController();
        String response;
        try {
            response = processCommand(ViewInterface.getInput());
        } catch (GameException e) {
            response = e.toString();
        }
        ViewInterface.showResult(response);*/
    }

    @Override
    protected String processCommand(String command){
        //todo add this to other phases

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
        } else if (command.matches(GameRegexes.ATTACK.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.ATTACK.regex);
            Cell attackedCell = ((gameController.getCurrentTurnOpponentPlayer()).getGameBoard().getMonsterCardZone())[Integer.parseInt(matcher.group(1))];
            try {
                response = battlePhaseController.attack(Cell.getSelectedCell(), attackedCell);

            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SELECT.regex)) {
            response = processSelect(command);
        } else if (command.matches(GameRegexes.ATTACK_DIRECT.regex)) {
           /* try {
                response = battlePhaseController.directAttack();
            } catch (GameException e) {
                response = e.toString();
            }*/
        } else if (command.matches(GameRegexes.SHOW_CARD_SELECTED.regex)) {
            try {
                response = gameController.showCard();
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SHOW_GRAVEYARD.regex)) {
            try {
                gameController.currentPhase = GamePhase.GRAVEYARD;
                response = gameController.showGraveyard();
            } catch (GameException e) {
                response = e.toString();
            }

        }
        else if(command.matches(GameRegexes.SURRENDER.regex)){
            gameController.surrender();
        }else {
            response = GameResponses.INVALID_COMMAND.response;
        }
        return response;
    }

    public static void main(String[] args) {

    }
}
