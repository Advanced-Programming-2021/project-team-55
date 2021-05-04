package controller.gamephasescontrollers;

import exceptions.GameException;
import model.Player;
import model.board.Cell;
import model.board.Game;
import model.cards.Card;
import model.cards.Monster;
import view.gamephases.BattlePhase;
import view.gamephases.GameResponses;

import static model.board.CardStatus.OFFENSIVE_OCCUPIED;
import static model.board.Cell.removeCardFromCell;

public class BattlePhaseController implements methods {
    private GameController gameController;

    public BattlePhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public void startBattlePhase() {

    }

    public String attack(Cell attackerCell, Cell attackedCell) throws GameException {
        System.out.println("in attack");
        Card attackerCard = attackerCell.getCellCard();
        Card attackedCard = attackedCell.getCellCard();
        if (attackerCard == null)
            return "Error: no card is selected yet";
        else if (attackedCard == null)
            return "Error: there is no card to attack here";


        if (isAttackerStronger((Monster) attackerCard, (Monster) attackedCard)) {
            decreasePlayersDamage((Monster) attackerCard, (Monster) attackedCard);
            removeCardFromCell(attackedCell);
            return "your opponent’s monster is destroyed and your opponent receives"
                    + calculateDamage((Monster) attackerCard, (Monster) attackedCard) + "battle damage";
        } else if (((Monster) attackedCard).getCardStatus() == OFFENSIVE_OCCUPIED) {
            if (isAttackerAndAttackedPowerEqual((Monster) attackerCard, (Monster) attackedCard)) {
                removeCardFromCell(attackedCell);
                removeCardFromCell(attackerCell);
                return "both you and your opponent monster cards are destroyed and no one receives damage";
            } else {
                decreasePlayersDamage((Monster) attackerCard, (Monster) attackedCard);
                removeCardFromCell(attackerCell);
                return "Your monster card is destroyed and you received" +
                        calculateDamage((Monster) attackerCard, (Monster) attackedCard) + "battle damage";
            }

        } else {

        }
        return null;
        //todo remove players cards
    }

    private void decreasePlayersDamage(Monster attackerCard, Monster attackedCard) {
        if (isAttackerStronger(attackerCard, attackedCard))
            (gameController.getCurrentTurnPlayer()).decreaseLP(calculateDamage(attackerCard, attackedCard));
        else
            (gameController.getCurrentTurnOpponentPlayer()).decreaseLP(calculateDamage(attackerCard, attackedCard));
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
        return true;
    }

    public String directAttack(GameController gameController) throws GameException {
        Player currentPlayer = gameController.currentTurnPlayer;
        Cell selectedCell = Cell.getSelectedCell();
        if (selectedCell == null) {
            throw new GameException(GameResponses.NO_CARDS_SELECTED.response);
        }
        if (!currentPlayer.getGameBoard().hasMonsterCardZoneCell(selectedCell)) {
            throw new GameException(GameResponses.CAN_NOT_ATTACK_WITH_THIS_CARD.response);
        }
        if (gameController.doCardAttackedThisTurn(selectedCell)) {
            throw new GameException(GameResponses.CARD_ALREADY_ATTACKED.response);
        }
        if (!gameController.canPlayerDirectAttack(selectedCell)) {
            throw new GameException(GameResponses.CAN_NOT_DIRECT_ATTACK.response);
        }
        Monster attackerMonster = (Monster) selectedCell.getCellCard();
        gameController.getCurrentTurnOpponentPlayer().decreaseLP(attackerMonster.getAtk());
        return "your opponent receives " + attackerMonster.getAtk() + " battle damage";
    }

}
