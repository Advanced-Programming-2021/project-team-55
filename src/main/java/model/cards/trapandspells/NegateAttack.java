package model.cards.trapandspells;

import controller.gamephasescontrollers.GameController;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.SpellAndTrap;
import model.cards.cardfeaturesenums.EffectiveTerm;
import model.cards.cardfeaturesenums.SpellOrTrap;
import model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import view.ViewInterface;

public class NegateAttack extends SpellAndTrap {

    public NegateAttack() {
        super("Negate Attack", "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, then end the Battle Phase.",
                3000, false, SpellOrTrap.TRAP, SpellOrTrapAttribute.COUNTER, EffectiveTerm.UNLIMITED);
    }
    public static void setActivated(GameController gameController){
        gameController.getBattlePhaseController().setAttackDisabled();
        ViewInterface.showResult("Negate Attack effect activated : attack is negated and opponent Battle Phase ended");
        gameController.changePhase();
        updateSpellInGameBoard(gameController);
    }

}