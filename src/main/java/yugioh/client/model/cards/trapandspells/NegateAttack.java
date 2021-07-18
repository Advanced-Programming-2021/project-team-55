package yugioh.client.model.cards.trapandspells;

import yugioh.client.controller.gamephasescontrollers.GameController;
import yugioh.client.model.User;
import yugioh.client.model.board.Cell;
import yugioh.client.model.cards.SpellAndTrap;
import yugioh.client.model.cards.cardfeaturesenums.EffectiveTerm;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrap;
import yugioh.client.model.cards.cardfeaturesenums.SpellOrTrapAttribute;
import yugioh.client.view.ViewInterface;

public class NegateAttack extends SpellAndTrap {
    public static Cell attackerCell;

    public NegateAttack() {
        super("Negate Attack", "When an opponent's monster declares an attack: Target the attacking monster; negate the attack, then end the Battle Phase.",
                3000, SpellOrTrap.TRAP, SpellOrTrapAttribute.COUNTER, EffectiveTerm.UNLIMITED);
    }

    public static void setActivated(GameController gameController) {
        gameController.getBattlePhaseController().setAttackDisabled();
        //  gameController.currentTurnOpponentPlayer.decreaseLP(((Monster)attackerCell.getCellCard()).getAtk());
        ViewInterface.showResult("Negate Attack effect activated : attack is negated and opponent Battle Phase ended");
        updateSpellInGameBoard(gameController);
        if(User.loggedInUser.equals(gameController.currentTurnPlayer.getUser()))
        gameController.changePhase();

    }

}