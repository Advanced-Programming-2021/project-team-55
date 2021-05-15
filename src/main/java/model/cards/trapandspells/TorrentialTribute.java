package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;

public class TorrentialTribute extends SpellAndTrap {

    public TorrentialTribute() {
        super("Torrential Tribute", "When a monster(s) is Summoned: Destroy all monsters on the field.",
                2000, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
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
            if(!monster.isEmpty()) {
                monster.removeCardFromCell(gameBoard);
            }
        }
    }


}