package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;
import view.gamephases.GameResponses;

public class HarpiesFeatherDuster extends SpellAndTrap {

    public HarpiesFeatherDuster() {
        super("Harpie's Feather Duster", "Destroy all Spells and Traps your opponent controls.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard opponentPlayerGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        if (opponentPlayerGameBoard.isSpellAndTrapCardZoneEmpty()) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        for (Cell cell : opponentPlayerGameBoard.getSpellAndTrapCardZone()) {
            if (!cell.isEmpty()) {
                cell.removeCardFromCell(opponentPlayerGameBoard);
            }
        }
        updateSpellInGameBoard(gameController);
        ViewInterface.showResult("Harpie's Feather Duster activated : all spells and traps your opponent controls destroyed.");
    }

}