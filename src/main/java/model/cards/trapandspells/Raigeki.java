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

public class Raigeki extends SpellAndTrap {

    public Raigeki() {
        super("Raigeki", "Destroy all monsters your opponent controls.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.LIMITED);
    }

    public static void setActivated(GameController gameController) {
        GameBoard opponentPlayerGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        if (!opponentPlayerGameBoard.doesMonsterZoneHaveMonsters(1)) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        for (Cell cell : opponentPlayerGameBoard.getMonsterCardZone()) {
            if (!cell.isEmpty()) {
                cell.removeCardFromCell(opponentPlayerGameBoard);
            }
        }
        updateSpellInGameBoard(gameController);
        ViewInterface.showResult("Raigeki activated : all opponents monsters destroyed.");

    }

}