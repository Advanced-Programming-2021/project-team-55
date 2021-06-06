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