package yugioh.client.model.cards.trapandspells;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.Cell;
import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.client.model.exceptions.GameException;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.gamephases.GameResponses;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MonsterReborn extends SpellAndTrap {

    public MonsterReborn() {
        super("Monster Reborn", "Target 1 monster in either GY; Special Summon it.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

    public static void setActivated(GameController gameController) {
        ArrayList<Cell> playerGraveyardMonstersCell = gameController.currentTurnPlayer.getGameBoard().getGraveyardMonstersCell();
        ArrayList<Cell> opponentGraveyardMonstersCell = gameController.currentTurnOpponentPlayer.getGameBoard().getGraveyardMonstersCell();
        if (opponentGraveyardMonstersCell.size() == 0 && playerGraveyardMonstersCell.size() == 0) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        if (!gameController.getMainPhase1Controller().canSpecialSummon(gameController)) {
            ViewInterface.showResult(GameResponses.NO_WAY_TO_SPECIAL_SUMMON.response);
            return;
        }
        ViewInterface.showResult("Monster Reborn activated : target 1 monster in either GY; Special Summon it.");
        ViewInterface.showResult("your graveyard monsters:");
        int counterPlayer = 0;
        for (Cell cell : playerGraveyardMonstersCell) {
            counterPlayer++;
            System.out.println(counterPlayer + ". " + cell.getCellCard());
        }

        ViewInterface.showResult("your opponent graveyard:");
        int counterOpponent = 0;
        for (Cell cell : opponentGraveyardMonstersCell) {
            counterOpponent++;
            System.out.println(counterOpponent + ". " + cell.getCellCard());
        }
        ViewInterface.showResult("choose a monster from your or your opponents graveyard: (me (number)/opponent (number))");
        String input;
        while (true) {
            input = ViewInterface.getInput();
            Matcher matcher;
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the effect of your card!");
                return;
            }
            if (input.matches("opponent (\\d+)")) {
                matcher = ViewInterface.getCommandMatcher(input, "opponent (\\d+)");
                int choice = Integer.parseInt(matcher.group(1));
                if (counterOpponent < choice || choice < 1) {
                    ViewInterface.showResult("Error: there is no monster with this number!");
                    continue;
                }
                updateSpellInGameBoard(gameController);
                Cell.setSelectedCell(opponentGraveyardMonstersCell.get(choice - 1));
                opponentGraveyardMonstersCell.remove(choice - 1);
                try {
                    gameController.getMainPhase1Controller().specialSummon(gameController);
                } catch (GameException e) {
                }


            } else if (input.matches("me (\\d+)")) {
                matcher = ViewInterface.getCommandMatcher(input, "me (\\d+)");
                int choice = Integer.parseInt(matcher.group(1));
                if (counterPlayer < choice || choice < 1) {
                    ViewInterface.showResult("Error: there is no monster with this number!");
                    continue;
                }
                updateSpellInGameBoard(gameController);
                Cell.setSelectedCell(playerGraveyardMonstersCell.get(choice - 1));
                playerGraveyardMonstersCell.remove(choice - 1);
                try {
                    gameController.getMainPhase1Controller().specialSummon(gameController);
                } catch (GameException e) {
                }
                return;
            } else {
                ViewInterface.showResult("Error: invalid format! try again: choose a monster from your or your opponents graveyard");
            }
        }
    }
}