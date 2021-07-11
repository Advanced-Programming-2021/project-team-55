package yugioh.server.model.cards.trapandspells;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;
import yugioh.server.view.gamephases.GameResponses;

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