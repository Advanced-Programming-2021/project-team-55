package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.board.GameBoard;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;

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