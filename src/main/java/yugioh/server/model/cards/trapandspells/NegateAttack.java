package yugioh.server.model.cards.trapandspells;

import yugioh.server.controller.gamephasescontrollers.GameController;
import yugioh.server.model.cards.SpellAndTrap;
import yugioh.server.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.server.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.server.view.ViewInterface;

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