package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;
import view.gamephases.Duel;
import view.gamephases.GameResponses;


public class TwinTwisters extends SpellAndTrap {

    public TwinTwisters() {
        super("Twin Twisters", "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.",
                3500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        if (!canActivate(gameController)) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }

        Cell oldSelectedCell = Cell.getSelectedCell();
        ViewInterface.showResult("Twin Twisters activated: choose a card from your hand.");
        while (true) {
            String selectionCommand = ViewInterface.getInput();
            if (!selectionCommand.startsWith("select --hand")) {
                if (selectionCommand.equals("cancel")) {
                    ViewInterface.showResult("you cancelled the effect of your card!");
                    return;
                }
                ViewInterface.showResult("Error: you should select a card from hand!");
                continue;
            }
            String result = Duel.processSelect(selectionCommand);
            if (!result.equals("card selected")) {
                ViewInterface.showResult("Error: try again!");
                continue;
            }
            Cell.getSelectedCell().removeCardFromCell(gameController.getCurrentTurnPlayer().getGameBoard());
            ViewInterface.showResult("hand card removed from your hand.\nnow choose at most 2 opponent spell or traps to be removed:");
            break;
        }

        int counter = 0;
        while (counter < 2) {
            String selectionCommand = ViewInterface.getInput();
            if (!selectionCommand.matches("select --spell \\d+ --opponent") && !selectionCommand.matches("select --opponent --spell \\d+")) {
                if (selectionCommand.equals("cancel")) {
                    ViewInterface.showResult("you cancelled the effect of your card!");
                    return;
                }
                ViewInterface.showResult("Error: you should select a spell or trap card from your opponent gameBoard!");
                continue;
            }
            String result = Duel.processSelect(selectionCommand);
            if (!result.equals("card selected")) {
                ViewInterface.showResult("Error: try again! you should select a spell or trap on the field to destroy");
                continue;
            }
            counter++;
            Cell.getSelectedCell().removeCardFromCell(gameController.getCurrentTurnOpponentPlayer().getGameBoard());
            ViewInterface.showResult("spell or trap " + counter + " destroyed");
            if (counter == 1) ViewInterface.showResult("select second spell or trap, or cancel the process");
        }

        Cell.setSelectedCell(oldSelectedCell);
        updateSpellInGameBoard(gameController);
    }

    private static boolean canActivate(GameController gameController) {
        return !gameController.getCurrentTurnPlayer().getGameBoard().isHandCardEmpty();
    }

}