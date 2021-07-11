package yugioh.server.model.cards.trapandspells;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.GameBoard;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;
import yugioh.server.view.gamephases.GameResponses;

public class PotofGreed extends SpellAndTrap {

    public PotofGreed() {
        super("Pot of Greed", "Draw 2 cards.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard playerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        if (playerGameBoard.getDeckZone().size() < 2 || playerGameBoard.getHandCards().size() > 4) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        ViewInterface.showResult("Pot of Greed activated : " + playerGameBoard.getDeckZone().get(0).getCellCard().getName() +
                " and " + playerGameBoard.getDeckZone().get(1).getCellCard().getName() + " added to the hand");
        playerGameBoard.addCardsToHandDeck(2);
        updateSpellInGameBoard(gameController);
    }

}