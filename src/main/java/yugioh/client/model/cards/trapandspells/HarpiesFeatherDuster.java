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

public class HarpiesFeatherDuster extends SpellAndTrap {

    public HarpiesFeatherDuster() {
        super("Harpie's Feather Duster", "Destroy all Spells and Traps your opponent controls.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard opponentPlayerGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        if (opponentPlayerGameBoard.isSpellAndTrapCardZoneEmpty()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        for (Cell cell : opponentPlayerGameBoard.getSpellAndTrapCardZone()) {
            if (!cell.isEmpty()) {
                cell.removeCardFromCell(opponentPlayerGameBoard, false);
            }
        }
        updateSpellInGameBoard(gameController);
        ViewInterface.showResult("Harpie's Feather Duster activated : all spells and traps your opponent controls destroyed.");
    }

}