package controller.gamephasescontrollers;

import exceptions.GameException;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.Card;
import model.cards.Monster;

import static model.board.Cell.removeCardFromCell;

public class BattlePhaseController implements methods {
    private final GameController gameController;

    public BattlePhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void startBattlePhase() {

    }

    public void attack( Cell attackerCell, Cell attackedCell)throws GameException {
        Card attackerCard = attackerCell.getCellCard();
        Card attackedCard = attackedCell.getCellCard();
        decreasePlayersDamage((Monster) attackerCard, (Monster) attackedCard);
        if (isAttackerStronger((Monster) attackerCard, (Monster) attackedCard)){
            removeCardFromCell(attackedCell);
        }
        else if (isAttackerAndAttackedPowerEqual((Monster) attackerCard, (Monster) attackedCard))
            return;

        //todo remove players cards
    }

    private void decreasePlayersDamage(Monster attackerCard, Monster attackedCard) {
        if (isAttackerStronger(attackerCard, attackedCard)) {
            (gameController.getCurrentTurnPlayer()).decreaseLP(calculateDamage(attackerCard, attackedCard));
        }
        else {
            (gameController.getCurrentTurnOpponentPlayer()).decreaseLP(calculateDamage(attackerCard, attackedCard));
        }
    }

    public boolean isAttackerStronger(Monster attackerCard, Monster attackedCard) {
        return attackerCard.getPower() > attackedCard.getPower();
    }

    public boolean isAttackerAndAttackedPowerEqual(Monster attackerCard, Monster attackedCard) {
        return attackerCard.getPower() == attackedCard.getPower();
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
