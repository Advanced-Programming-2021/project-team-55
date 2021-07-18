package yugioh.server.model.cards.trapandspells;

import yugioh.client.view.menus.PopUpWindow;
import yugioh.client.view.menus.WelcomeMenu;
import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;
import yugioh.server.view.gamephases.Duel;
import yugioh.server.view.gamephases.GameResponses;


public class TwinTwisters extends SpellAndTrap {

    public TwinTwisters() {
        super("Twin Twisters", "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.",
                3500, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        if (!canActivate(gameController)) {
            try {
                new PopUpWindow(GameResponses.PREPARATION_NOT_DONE.response).start(WelcomeMenu.stage);
            } catch (Exception e) {
            }
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