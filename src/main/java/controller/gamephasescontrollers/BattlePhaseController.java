package controller.gamephasescontrollers;

import exceptions.GameException;
import model.Player;
import model.board.Game;
import model.cards.Card;
import model.cards.Monster;
import view.gamephases.BattlePhase;

public class BattlePhaseController implements methods {
    private GameController gameController;

    public BattlePhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void startBattlePhase() {

    }

    public void attack(Monster attackerCard, Monster attackedCard) throws GameException {
        decreasePlayersDamage(attackerCard, attackedCard);

        //todo remove players cards
    }

    private void decreasePlayersDamage(Monster attackerCard, Monster attackedCard) {
        if (isAttackerStronger(attackerCard, attackedCard))
            (gameController.getCurrentTurnPlayer()).decreaseLP(calculateDamage(attackerCard, attackedCard));
        else
            (gameController.getCurrentTurnOpponentPlayer()).decreaseLP(calculateDamage(attackerCard, attackedCard));
    }

    public boolean isAttackerStronger(Monster attackerCard, Monster attackedCard){
        return attackerCard.getPower() > attackedCard.getPower();
    }

    public int calculateDamage(Monster attackerCard, Monster attackedCard) {
        int damage = attackedCard.getPower() - attackerCard.getPower();
        if (damage >= 0)
            return damage;
        else
            return -damage;
    }

    public boolean canCardAttack(Card card) {
        return false;
    }


}
