package yugioh.view.gamephases;

import yugioh.controller.AIPlayerController;
import yugioh.controller.CheatController;
import yugioh.controller.gamephasescontrollers.MainPhase1Controller;
import yugioh.controller.menucontroller.GameMenuController;
import yugioh.model.board.Cell;
import yugioh.model.cards.Monster;
import yugioh.model.exceptions.GameException;
import yugioh.view.GameRegexes;
import yugioh.view.ViewInterface;

import java.util.regex.Matcher;

public class MainPhase1 extends Duel {
    private static MainPhase1Controller mainPhase1Controller;


    @Override
    protected void execute() {
        mainPhase1Controller = gameController.getMainPhase1Controller();

//        ViewInterface.showResult(mainPhase1Controller.showGameBoard(gameController.currentTurnPlayer,
//                gameController.currentTurnOpponentPlayer));

//        String response = "";//todo uncomment
//        if (Duel.getGameController().getCurrentTurnPlayer().isAI()) {
//            AIPlayerController aiPlayerController = (new AIPlayerController(AIPlayerController.orderKind.RANDOM,
//                    AIPlayerController.orderKind.RANDOM));
//            String AICommand = "";
//            response = processCommand(AICommand);
//            while (response.startsWith("Error: ") && !AICommand.equals("next phase")) {
//                AICommand = aiPlayerController.getSelectCommandForMainPhases();
//                response = processCommand(AICommand);
//                if (AICommand.equals("next phase")) break;
//                AICommand = aiPlayerController.getMainCommandForMainPhases();
//                response = processCommand(AICommand);
//            }
//        } else {
//            response = processCommand(ViewInterface.getInput());
//        }
//        ViewInterface.showResult(response);
    }

    @Override
    protected String processCommand(String command) {
        String response = "";
        if (command.equals("show attack")) {
            System.out.println(((Monster) Cell.getSelectedCell().getCellCard()).getAtk());
        } else if (command.equals("remove card"))
            Cell.getSelectedCell().removeCardFromCell(gameController.currentTurnPlayer.getGameBoard());
        else if (gameController.checkCommandIsNotInCurrentPhase(command)) {
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
        } else if (command.matches(GameRegexes.MAKE_ME_AI.regex)) {
            response = CheatController.getInstance().makeMeAI(gameController);
        } else if (command.matches(GameRegexes.SUMMON.regex)) {
            try {
                mainPhase1Controller.monsterSummon(gameController);
                if (!gameController.shouldSpecialSummonNow)
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
            gameController.currentPhase = GamePhase.GRAVEYARD;
            response = gameController.showGraveyard();

        } else if (command.matches(GameRegexes.ACTIVATE_EFFECT.regex)) {
            try {
                mainPhase1Controller.activateSpell(gameController);
            } catch (GameException e) {
                response = e.toString();
            }
        }
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

    public static MainPhase1Controller getMainPhase1Controller() {
        return mainPhase1Controller;
    }
}