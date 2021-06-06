package yugioh.model.cards.trapandspells;

import yugioh.controller.gamephasescontrollers.GameController;
import yugioh.model.cards.SpellAndTrap;
import yugioh.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.view.ViewInterface;

public class NegateAttack extends SpellAndTrap {

    public NegateAttack() {
        super("Negate Attack", "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, then end the Battle Phase.",
                3000, SpellOrTrap.TRAP, SpellOrTrapAttribute.COUNTER, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        gameController.getBattlePhaseController().setAttackDisabled();
        ViewInterface.showResult("Negate Attack effect activated : attack is negated and opponent Battle Phase ended");
        gameController.changePhase();
        updateSpellInGameBoard(gameController);
    }

}