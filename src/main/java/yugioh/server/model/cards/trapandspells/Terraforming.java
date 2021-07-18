package yugioh.server.model.cards.trapandspells;

import yugioh.client.view.menus.PopUpWindow;
import yugioh.client.view.menus.WelcomeMenu;
import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.board.GameBoard;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;
import yugioh.server.view.gamephases.GameResponses;

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
                playerGameBoard.addCardToHandDeck(fieldSpell.getCellCard().getName());
                break;
            }
        }
        if (fieldSpell.isEmpty()) {
            try {
                new PopUpWindow(GameResponses.PREPARATION_NOT_DONE.response).start(WelcomeMenu.stage);
            } catch (Exception e) {
            }
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        } else {
            ViewInterface.showResult("Monster Reborn activated : " + fieldSpell.getCellCard().getName() + " added to hand.");
            updateSpellInGameBoard(gameController);
        }
    }
}