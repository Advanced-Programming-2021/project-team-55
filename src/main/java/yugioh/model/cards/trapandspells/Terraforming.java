package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.board.GameBoard;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.GameResponses;

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
                playerGameBoard.addCardToHandDeck(fieldSpell.getCellCard(),false);
                break;
            }
        }
        if (fieldSpell.isEmpty()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        } else {
            ViewInterface.showResult("Monster Reborn activated : " + fieldSpell.getCellCard().getName() + " added to hand.");
            updateSpellInGameBoard(gameController);
        }
    }
}