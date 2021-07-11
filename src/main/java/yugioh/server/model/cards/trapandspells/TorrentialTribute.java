package yugioh.server.model.cards.trapandspells;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.board.GameBoard;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;

public class TorrentialTribute extends SpellAndTrap {

    public TorrentialTribute() {
        super("Torrential Tribute", "When a monster(s) is Summoned: Destroy all monsters on the field.",
                2000, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        Cell[] currentTurnPlayerMonsterZone = gameController.getCurrentTurnPlayer().getGameBoard().getMonsterCardZone();
        Cell[] currentTurnOpponentPlayerMonsterZone = gameController.getCurrentTurnOpponentPlayer().getGameBoard().getMonsterCardZone();
        destroyAPlayerMonsters(currentTurnPlayerMonsterZone, gameController.getCurrentTurnPlayer().getGameBoard());
        destroyAPlayerMonsters(currentTurnOpponentPlayerMonsterZone, gameController.getCurrentTurnOpponentPlayer().getGameBoard());
        updateSpellInGameBoard(gameController);
        ViewInterface.showResult("Torrential Tribute activated : all monsters are destroyed");
    }

    private static void destroyAPlayerMonsters(Cell[] monsterCardZone, GameBoard gameBoard) {
        for (Cell monster : monsterCardZone
        ) {
            if (!monster.isEmpty()) {
                monster.removeCardFromCell(gameBoard);
            }
        }
    }
}