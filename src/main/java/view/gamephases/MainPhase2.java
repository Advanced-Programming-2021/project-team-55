package view.gamephases;

import controller.gamephasescontrollers.MainPhase2Controller;
import exceptions.GameException;
import view.GameRegexes;
import view.ViewInterface;

import java.util.regex.Matcher;

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
        if (!gameController.checkCommandIsInCurrentPhase(command)) {
            response = GameResponses.ACTION_NOT_ALLOWED_FOR_THIS_PHASE.response;
        } else if (command.matches(GameRegexes.NEXT_PHASE.regex)) {
            gameController.changePhase();
            showPhase();
        } else if (command.matches(GameRegexes.DESELECT.regex)) {
            try {
                gameController.deselect();
                response = GameResponses.CARD_DESELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SELECT.regex)) {
            response = processSelect(command);
        } else if (command.matches(GameRegexes.SHOW_CARD_SELECTED.regex)) {
            try {
                response = gameController.showCard();
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SUMMON.regex)) {
            try {
                mainPhase2Controller.monsterSummon(gameController);
                response = GameResponses.SUMMONED_SUCCESSFULLY.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SET.regex)) {
            try {
                mainPhase2Controller.setCard(gameController);
                response = GameResponses.SET_SUCCESSFULLY.response;
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
        } else if (command.matches(GameRegexes.SURRENDER.regex)) {
            gameController.surrender();
        } else if (command.matches(GameRegexes.INCREASE_LP.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.INCREASE_LP.regex);
            cheatController.increaseLPAmount(Integer.parseInt(matcher.group(1)), gameController.currentTurnPlayer);
            response = GameResponses.CHEAT_ACTIVATED_LP_INCREASED.response;
        } else if (command.matches(GameRegexes.SET_WINNER.regex)) {
            cheatController.setWinner(gameController);
            response = GameResponses.CHEAT_ACTIVATED_WINNER_SET.response;
        } else if (command.matches(GameRegexes.SELECT_CARD_FORCE.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SELECT_CARD_FORCE.regex);
            try {
                response = cheatController.selectHandForce(matcher.group(1), gameController);
            } catch (GameException e) {
                response = e.toString();
            }
        } else {
            response = GameResponses.INVALID_COMMAND.response;
        }
        return response;
    }

}
