package yugioh.server.model.cards.trapandspells;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.board.GameBoard;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;

public class DarkHole extends SpellAndTrap {


    public DarkHole() {
        super("Dark Hole", "Destroy all monsters on the field.",
                2500, SpellOrTrap.SPELL, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        handleEffect(gameController);
        ViewInterface.showResult("Dark Hole activated : all game monsters are destroyed.");
        updateSpellInGameBoard(gameController);
    }

    private static void handleEffect(GameController gameController) {
        GameBoard currentTurnPlayerGameBoard = gameController.getCurrentTurnPlayer().getGameBoard();
        GameBoard currentTurnOpponentPlayerGameBoard = gameController.getCurrentTurnOpponentPlayer().getGameBoard();
        removeOnePlayerMonsters(currentTurnPlayerGameBoard);
        removeOnePlayerMonsters(currentTurnOpponentPlayerGameBoard);
    }

    private static void removeOnePlayerMonsters(GameBoard currentTurnPlayerGameBoard) {
        for (Cell monster : currentTurnPlayerGameBoard.getMonsterCardZone()) {
            if (!monster.isEmpty())
                monster.removeCardFromCell(currentTurnPlayerGameBoard);
        }
    }

}