package view.gamephases;

import controller.AIPlayerController;
import controller.gamephasescontrollers.BattlePhaseController;
import model.exceptions.GameException;
import view.GameRegexes;
import view.ViewInterface;

import java.util.regex.Matcher;

public class BattlePhase extends Duel {
    private static BattlePhaseController battlePhaseController;

    @Override
    protected void execute() {
        battlePhaseController = gameController.getBattlePhaseController();
        if (gameController.turnCount == 1) {
            gameController.changePhase();
        } else {
            String response;
            if (Duel.getGameController().getCurrentTurnPlayer().isAI()) {
                AIPlayerController aiPlayerController = (new AIPlayerController(AIPlayerController.orderKind.RANDOM,
                        AIPlayerController.orderKind.RANDOM));
                String AICommand = "";
                response = processCommand(AICommand);
                while (response.startsWith("Error: ") && !AICommand.equals("next phase")) {
                    AICommand = aiPlayerController.getSelectCommandForBattlePhase();
                    response = processCommand(AICommand);
                    if (AICommand.equals("next phase")) break;
                    AICommand = aiPlayerController.getMainCommandForBattlePhase();
                    response = processCommand(AICommand);
                }
            } else {
                response = processCommand(ViewInterface.getInput());
            }
            ViewInterface.showResult(response);
        }
    }

    @Override
    protected String processCommand(String command) {
        String response = "";

        if (gameController.checkCommandIsNotInCurrentPhase(command)) {
            response = GameResponses.ACTION_NOT_ALLOWED_FOR_THIS_PHASE.response;
        } else if (command.matches(GameRegexes.NEXT_PHASE.regex)) {
            gameController.changePhase();
        } else if (command.matches(GameRegexes.DESELECT.regex)) {
            try {
                gameController.deselect();
                response = GameResponses.CARD_DESELECTED.response;
            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.SELECT.regex)) {
            response = processSelect(command);
        } else if (command.matches(GameRegexes.ATTACK.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.ATTACK.regex);
            try {
                response = battlePhaseController.attack(Integer.parseInt(matcher.group(1)));

            } catch (GameException e) {
                response = e.toString();
            }
        } else if (command.matches(GameRegexes.ATTACK_DIRECT.regex)) {
            try {
                response = battlePhaseController.directAttack(gameController);
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
            response = gameController.showGraveyard();
        } else if (command.matches(GameRegexes.SURRENDER.regex)) {
            gameController.surrender();
        } else if (command.matches(GameRegexes.INCREASE_LP.regex)) {
            Matcher matcher = ViewInterface.getCommandMatcher(command, GameRegexes.INCREASE_LP.regex);
            cheatController.increaseLPAmount(Integer.parseInt(matcher.group(1)), gameController.currentTurnPlayer);
            response = GameResponses.CHEAT_ACTIVATED_LP_INCREASED.response;
        } else if (command.matches(GameRegexes.SET_WINNER.regex)) {
            cheatController.setWinner(gameController);
            response = GameResponses.CHEAT_ACTIVATED_WINNER_SET.response;
        } else {
            response = GameResponses.INVALID_COMMAND.response;
        }
        return response;
    }

}
