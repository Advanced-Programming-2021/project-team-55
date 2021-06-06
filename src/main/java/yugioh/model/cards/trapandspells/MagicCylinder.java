package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.cards.Monster;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;
import yugioh.view.gamephases.GameResponses;

public class MagicCylinder extends SpellAndTrap {

    public MagicCylinder() {
        super("Magic Cylinder", "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, and if you do, inflict damage to your opponent equal to its ATK.",
                2000, SpellOrTrap.TRAP, SpellOrTrapAttribute.NORMAL, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        if (gameController.getBattlePhaseController().getAttacker()==null) {
            ViewInterface.showResult(GameResponses.PREPARATION_NOT_DONE.response);
            return;
        }
        gameController.getBattlePhaseController().setAttackDisabled();
        int attackDamage = ((Monster) (gameController.getBattlePhaseController().getAttacker().getCellCard())).getAtk();
        gameController.getCurrentTurnOpponentPlayer().decreaseLP(attackDamage);
        ViewInterface.showResult("Magic Cylinder effect activated : attack is negated and and opponent takes " +
                attackDamage + " damage");
        updateSpellInGameBoard(gameController);
    }

}