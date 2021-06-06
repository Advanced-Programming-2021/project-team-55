package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.board.Cell;
import yugioh.model.board.GameBoard;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;

public class TrapHole extends SpellAndTrap {

    public TrapHole() {
        super("Trap Hole", "When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: Target that monster; destroy that target.",
                2000, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        destroySummonedMonster(gameController.getLastSummonedMonster(), gameController.getCurrentTurnOpponentPlayer().getGameBoard());
        updateSpellInGameBoard(gameController);
        ViewInterface.showResult("Trap Hole effect activated : summoned monster is destroyed");
    }

    private static void destroySummonedMonster(Cell summonedMonster, GameBoard gameBoard) {
        summonedMonster.removeCardFromCell(gameBoard);
    }

}