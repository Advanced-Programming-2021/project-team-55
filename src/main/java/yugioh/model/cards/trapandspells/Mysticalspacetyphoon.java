package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.board.GameBoard;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.Duel;
import yugioh.view.gamephases.GameResponses;

public class Mysticalspacetyphoon extends SpellAndTrap {

    public Mysticalspacetyphoon() {
        super("Mystical space typhoon", "Target 1 Spell/Trap on the field; destroy that target.",
                3500, SpellOrTrap.SPELL, SpellOrTrapAttribute.QUICK_PLAY, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        GameBoard opponentGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        if (playerGameBoard.isSpellAndTrapCardZoneEmpty() && opponentGameBoard.isSpellAndTrapCardZoneEmpty()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        Cell selectedCell = Cell.getSelectedCell();
        ViewInterface.showResult("Mystical space typhoon activated : select a spell or trap on the field to destroy:");
        String input = ViewInterface.getInput();
        while (true) {
            String response = Duel.processSelect(input);
            if (input.equals("cancel")) {
                ViewInterface.showResult("you cancelled the activation of Mystical space typhoon!");
                return;
            }
            if (input.matches("^select --spell (\\d+)$")) {
                if (response.equals(GameResponses.CARD_SELECTED.response)) {
                    ViewInterface.showResult("your card " + Cell.getSelectedCell().getCellCard().getName() + " destroyed.");
                    Cell.getSelectedCell().removeCardFromCell(playerGameBoard);
                    break;
                }
            } else if (input.matches("^select --opponent --spell (\\d+)$")) {
                if (response.equals(GameResponses.CARD_SELECTED.response)) {
                    ViewInterface.showResult("your opponent card " + Cell.getSelectedCell().getCellCard().getName() + " destroyed.");
                    Cell.getSelectedCell().removeCardFromCell(playerGameBoard);
                    break;
                }
            }
            ViewInterface.showResult("Error: try again. you should select a spell or trap on the field to destroy");
            input = ViewInterface.getInput();
        }
        Cell.setSelectedCell(selectedCell);
        updateSpellInGameBoard(gameController);
    }
}