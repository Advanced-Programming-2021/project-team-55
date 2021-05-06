package view.gamephases;

import controller.gamephasescontrollers.MainPhase1Controller;
import exceptions.GameException;
import view.GameRegexes;
import view.ViewInterface;

import java.util.regex.Matcher;

public class MainPhase1 extends Duel {
    private static MainPhase1Controller mainPhase1Controller;


    @Override
    protected void execute() {
        mainPhase1Controller = gameController.getMainPhase1Controller();

        ViewInterface.showResult(mainPhase1Controller.showGameBoard(gameController.currentTurnPlayer,
                gameController.currentTurnOpponentPlayer));
        String response = processCommand(ViewInterface.getInput());
        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (!gameController.checkCommandIsInCurrentPhase(command)) {
            response = GameResponses.ACTION_NOT_ALLOWED_FOR_THIS_PHASE.response;
        }
        else if (command.matches(GameRegexes.NEXT_PHASE.regex)) {
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
        } else if (command.matches(GameRegexes.SUMMON.regex)) {
            try {
                mainPhase1Controller.monsterSummon(gameController);
                response = GameResponses.SUMMONED_SUCCESSFULLY.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SET.regex)) {
            try {
                mainPhase1Controller.setCard(gameController);
                response = GameResponses.SET_SUCCESSFULLY.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SET_POSITION.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SET_POSITION.regex);
            try {
                mainPhase1Controller.setPosition(matcher.group(1), gameController);
                response = GameResponses.SET_POSITION_SUCCESSFULLY.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.FLIP_SUMMON.regex)) {
            try {
                mainPhase1Controller.flipSummon(gameController);
                response = GameResponses.FLIP_SUMMONED_SUCCESSFULLY.response;
            } catch (GameException e) {
                response = e.toString();
            }
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
        //todo these attack methods should be moved to battle phase
        else if (command.matches(GameRegexes.ACTIVATE_EFFECT.regex)) {
            try {
                mainPhase1Controller.activateSpell();
                response = GameResponses.SPELL_ACTIVATED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        }
        //todo check whether we have handled all methods in this phase or not
        else if (command.matches(GameRegexes.SURRENDER.regex)) {
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