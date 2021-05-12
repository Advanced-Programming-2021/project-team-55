package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;

public class DarkHole extends SpellAndTrap {

    public DarkHole() {
        super("Dark Hole", "Destroy all monsters on the field.",
                2500, false, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        handleEffect(gameController);
        ViewInterface.showResult("Dark Hole activated : all game monsters are destroyed.");
    }

    private static void handleEffect(GameController gameController) {
        GameBoard currentTurnPlayerGameBoard= gameController.getCurrentTurnPlayer().getGameBoard();
        GameBoard currentTurnOpponentPlayerGameBoard= gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        removeOnePlayerMonsters(currentTurnPlayerGameBoard);
        removeOnePlayerMonsters(currentTurnOpponentPlayerGameBoard);
    }

    private static void removeOnePlayerMonsters(GameBoard currentTurnPlayerGameBoard) {
        for (Cell monster: currentTurnPlayerGameBoard.getMonsterCardZone()) {
            if (!monster.isEmpty())
                monster.removeCardFromCell(currentTurnPlayerGameBoard);
        }
    }

}