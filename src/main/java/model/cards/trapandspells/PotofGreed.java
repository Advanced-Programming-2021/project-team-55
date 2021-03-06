package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.GameBoard;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;
import view.gamephases.GameResponses;

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