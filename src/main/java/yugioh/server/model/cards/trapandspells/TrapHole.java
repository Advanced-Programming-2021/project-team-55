package yugioh.server.model.cards.trapandspells;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.board.Cell;
import yugioh.server.model.board.GameBoard;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;

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