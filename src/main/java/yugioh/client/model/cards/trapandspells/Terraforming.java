package yugioh.client.model.cards.trapandspells;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.board.Cell;
import yugioh.client.model.board.GameBoard;
import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.client.view.ViewInterface;
import yugioh.client.view.gamephases.GameResponses;

public class Terraforming extends SpellAndTrap {

    public Terraforming() {
        super("Terraforming", "Add 1 Field Spell from your Deck to your hand.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

    public static void setActivated(GameController gameController) {
        Cell fieldSpell = new Cell();
        GameBoard playerGameBoard = gameController.currentTurnPlayer.getGameBoard();
        for (Cell cell : playerGameBoard.getDeckZone()) {
            if (cell.getCellCard().isSpell() && ((SpellAndTrap) cell.getCellCard()).getAttribute() == SpellOrTrapAttribute.FIELD) {
                fieldSpell = cell;
                playerGameBoard.addCardToHandDeck(fieldSpell.getCellCard(), false);
                break;
            }
        }
        if (fieldSpell.isEmpty()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        } else {
            ViewInterface.showResult("Terraforming activated : " + fieldSpell.getCellCard().getName() + " added to hand.");
            updateSpellInGameBoard(gameController);
        }
    }
}