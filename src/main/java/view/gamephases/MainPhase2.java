package view.gamephases;

import controller.AIPlayerController;
import controller.gamephasescontrollers.MainPhase2Controller;
import model.exceptions.GameException;
import view.GameRegexes;
import view.LoggerMessage;
import view.ViewInterface;

import java.util.regex.Matcher;

public class MainPhase2 extends Duel {
    private MainPhase2Controller mainPhase2Controller;


    @Override
    protected void execute() {
        mainPhase2Controller = gameController.getMainPhase2Controller();
        ViewInterface.showResult(mainPhase2Controller.showGameBoard(gameController.currentTurnPlayer,
                gameController.currentTurnOpponentPlayer));

        String response;
        if (Duel.getGameController().getCurrentTurnPlayer().isAI()) {
            AIPlayerController aiPlayerController = (new AIPlayerController(AIPlayerController.orderKind.RANDOM,
                    AIPlayerController.orderKind.RANDOM));
            String AICommand = "";
            response = processCommand(AICommand);
            while (response.startsWith("Error: ") && !AICommand.equals("next phase")) {
                AICommand =  aiPlayerController.getSelectCommandForMainPhases();
                response = processCommand(AICommand);
                if (AICommand.equals("next phase")) break;
                AICommand =  aiPlayerController.getMainCommandForMainPhases();
                response = processCommand(AICommand);
            }
        } else {
            response = processCommand(ViewInterface.getInput());
        }
            ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        //todo injaro badan azam beporsin tozih bedam bahaton check konam
       /* if (gameController.shouldRitualSummonNow) {
            if (command.matches(GameRegexes.SELECT.regex)) {
                response = processSelect(command);
            } else if (command.matches(GameRegexes.SUMMON.regex)) {
                try {
                    mainPhase2Controller.ritualSummon(gameController);
                    response=GameResponses.SUMMONED_SUCCESSFULLY.response;

                }catch (GameException e){
                    response=e.toString();
                }
            } else {
                response = GameResponses.YOU_SHOULD_RITUAL_SUMMON_NOW.response;
            }
        }*/
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
                mainPhase2Controller.monsterSummon(gameController);
                if (!gameController.shouldSpecialSummonNow)
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
        } else if (command.matches(GameRegexes.SET_POSITION.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.SET_POSITION.regex);
            try {
                mainPhase2Controller.setPosition(matcher.group(1), gameController);
                response = GameResponses.SET_POSITION_SUCCESSFULLY.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.FLIP_SUMMON.regex)) {
            try {
                mainPhase2Controller.flipSummon(gameController);
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
                gameController.currentPhase = GamePhase.GRAVEYARD;
                response = gameController.showGraveyard(gameController.currentTurnPlayer);

        }
        else if (command.matches(GameRegexes.ACTIVATE_EFFECT.regex)) {
            try {
                mainPhase2Controller.activateSpell(gameController);
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
        } else if (command.matches(GameRegexes.CHEAT_ADD_OPTIONAL_CARD.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.CHEAT_ADD_OPTIONAL_CARD.regex);
            try {
                response = cheatController.addOptionalCardAndSelect(matcher.group(1), gameController);
            } catch (GameException e) {
                response = e.toString();
            }
        } else {
            response = GameResponses.INVALID_COMMAND.response;
        }

        return response;
    }

}
