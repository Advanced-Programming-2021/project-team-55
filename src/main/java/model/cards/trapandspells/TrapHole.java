package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Monster;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;

public class TrapHole extends SpellAndTrap {

    public TrapHole() {
        super("Trap Hole", "When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: Target that monster; destroy that target.",
                2000, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        if (!isSummonedMonsterATKMoreThan1000(gameController)) return;
        destroySummonedMonster(gameController.getLastSummonedMonster(), gameController.getCurrentTurnOpponentPlayer().getGameBoard());
        updateSpellInGameBoard(gameController);
        ViewInterface.showResult("Trap Hole effect activated : summoned monster is destroyed");
    }

    public static boolean isSummonedMonsterATKMoreThan1000(GameController gameController) {
        return ((Monster) gameController.getLastSummonedMonster().getCellCard()).getAtk() >= 1000;
    }

    private static void destroySummonedMonster(Cell summonedMonster, GameBoard gameBoard) {
        summonedMonster.removeCardFromCell(gameBoard);
    }

}