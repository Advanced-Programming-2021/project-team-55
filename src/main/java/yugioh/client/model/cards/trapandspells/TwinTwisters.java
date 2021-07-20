package yugioh.client.model.cards.trapandspells;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.Cell;
import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.gamephases.Duel;
import yugioh.client.view.gamephases.GameResponses;


public class TwinTwisters extends SpellAndTrap {

    public TwinTwisters() {
        super("Twin Twisters", "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.",
                3500, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, EffectiveTerm.UNLIMITED);
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
            Cell.getSelectedCell().removeCardFromCell(gameController.getCurrentTurnPlayer().getGameBoard(), false);
            ViewInterface.showResult("hand card removed from your hand.\nnow choose at most 2 opponent spell or traps to be removed:");
            break;
        }

        int counter = 0;
        while (counter < 2) {
            String selectionCommand = ViewInterface.getInput();
//            String place="";
//            if(User.loggedInUser.equals(gameController.currentTurnPlayer.getUser())){
//                place=" --opponent";
//            }
            String place=" --opponent";
            if (!selectionCommand.matches("select --spell \\d+"+place) && !selectionCommand.matches("select"+place+" --spell \\d+")) {
                if (selectionCommand.equals("cancel")) {
                    ViewInterface.showResult("you cancelled the effect of your card!");
                    if(counter==1)
                    break;
                    else return;
                }
                ViewInterface.showResult("Error: you should select a spell or trap card from your opponent gameBoard!");
                continue;
            }
            String result = Duel.processSelect(selectionCommand);
            if (!result.equals("card selected")) {
                ViewInterface.showResult(result);
                continue;
            }
            counter++;
            gameController.currentTurnPlayer.getGameBoard().removeCardFromHand(Cell.getSelectedCell());
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