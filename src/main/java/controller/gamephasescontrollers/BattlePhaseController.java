package controller.gamephasescontrollers;

import exceptions.GameException;
import model.Player;
import model.board.Cell;
import model.board.Game;
import model.cards.Card;
import model.cards.Monster;
import view.gamephases.BattlePhase;
import view.gamephases.GameResponses;

import static model.board.CardStatus.DEFENSIVE_OCCUPIED;
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
        String response="";
        if (attackerCell == null)
            response="Error: no card is selected yet";
        else if (attackedCell.getCellCard()== null)
            response= "Error: there is no card to attack here";

        else if (attackedCell.getCardStatus() == OFFENSIVE_OCCUPIED) {
            if (isAttackerStronger(attackerCell, attackedCell)) {
                decreasePlayersDamage(attackerCell, attackedCell);
                response= "your opponent’s monster is destroyed and your opponent receives "
                        + calculateDamage(attackerCell, attackedCell) + " battle damage";
                removeCardFromCell(attackedCell);
            } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell)) {
                response= "both you and your opponent monster cards are destroyed and no one receives damage";
                removeCardFromCell(attackedCell);
                removeCardFromCell(attackerCell);
            } else {
                decreasePlayersDamage(attackerCell, attackedCell);
                response= "Your monster card is destroyed and you received " +
                        calculateDamage(attackerCell, attackedCell) + " battle damage";
                removeCardFromCell(attackerCell);
            }
        } else if (attackedCell.getCardStatus() == DEFENSIVE_OCCUPIED) {
            if (isAttackerStronger(attackerCell, attackedCell)) {
                //decreasePlayersDamage(attackerCell, attackedCell);
                response= "the defense position monster is destroyed";
                removeCardFromCell(attackedCell);
            } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell))
                response= "no card is destroyed";
            else {
                decreasePlayersDamage(attackerCell, attackedCell);
                response= "no card is destroyed and you received " +
                        calculateDamage(attackerCell, attackedCell) + " battle damage";
            }
        } else {
            if (isAttackerStronger(attackerCell, attackedCell)) {
                response= "opponent’s monster card was " +
                        attackedCell.getCellCard().getName() + " the defense position monster is destroyed";
                removeCardFromCell(attackedCell);
            } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell))
                response="opponent’s monster card was " +
                        attackedCell.getCellCard().getName() + " and no card is destroyed";
            else {
                decreasePlayersDamage(attackerCell, attackedCell);
                response= "opponent’s monster card was " + attackedCell.getCellCard().getName() +
                        " and no card is destroyed and you received " +
                        calculateDamage(attackerCell, attackedCell) + " battle damage";
            }
        }
        return response;
    }

    private void decreasePlayersDamage(Cell attackerCell, Cell attackedCell) {
        if (isAttackerStronger(attackerCell, attackedCell))
            (gameController.getCurrentTurnPlayer()).decreaseLP(calculateDamage(attackerCell, attackedCell));
        else
            (gameController.getCurrentTurnOpponentPlayer()).decreaseLP(calculateDamage(attackerCell, attackedCell));
    }

    public boolean isAttackerStronger(Cell attackerCell, Cell attackedCell) {
        return attackerCell.getPower() > attackedCell.getPower();
    }

    public boolean isAttackerAndAttackedPowerEqual(Cell attackerCell, Cell attackedCell) {
        return attackerCell.getPower() == attackedCell.getPower();
    }

    public int calculateDamage(Cell attackerCell, Cell attackedCell) {
        int damage = attackedCell.getPower() - attackerCell.getPower();
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
        if (gameController.didCardAttackedThisTurn(selectedCell)) {
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
