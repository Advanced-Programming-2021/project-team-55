package model.cards;

import controller.gamephasescontrollers.GameController;
import exceptions.GameException;
import model.board.CardStatus;
import model.board.Cell;
import model.board.GameBoard;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;
import model.cards.monsters.*;
import view.gamephases.GameResponses;

import static model.board.CardStatus.DEFENSIVE_OCCUPIED;
import static model.board.CardStatus.OFFENSIVE_OCCUPIED;


public abstract class Monster extends Card {

    private int atk;
    private int def;
    private int level;
    private MonsterAttribute attribute;
    private MonsterType monsterType;
    private CardType cardType;
    private CardStatus cardStatus;

    protected Monster(String cardName, String description, int price, int atk, int def, int level,
                      MonsterAttribute attribute, MonsterType monsterType, CardType cardType) {
        super(cardName, description, price, Kind.MONSTER);
        this.atk = atk;
        this.def = def;
        this.level = level;
        this.attribute = attribute;
        this.monsterType = monsterType;
        this.cardType = cardType;
    }

    public String handleMonsterAttack(Cell attackerCell, Cell attackedCell, GameBoard opponentGameBoard, GameBoard playerGameBoard, GameController gameController) throws GameException {
        String response;
        if (gameController.didCardAttackThisTurn(attackerCell)) {
            throw new GameException(GameResponses.ALREADY_ATTACKED_CARD.response);
        } else if (attackedCell == null || attackedCell.getCellCard() == null) {
            throw new GameException(GameResponses.NO_CARD_TO_ATTACK.response);
        } else if (attackedCell.getCardStatus() == OFFENSIVE_OCCUPIED) {
            if (isAttackerStronger(attackerCell, attackedCell)) {
                decreasePlayersDamage(attackerCell, attackedCell, gameController);
                response = "your opponent’s monster is destroyed and your opponent receives "
                        + calculateDamage(attackerCell, attackedCell) + " battle damage";
                attackedCell.removeCardFromCell(opponentGameBoard);
            } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell)) {
                response = "both you and your opponent monster cards are destroyed and no one receives damage";
                attackerCell.removeCardFromCell(playerGameBoard);
                attackedCell.removeCardFromCell(opponentGameBoard);
            } else {
                decreasePlayersDamage(attackerCell, attackedCell, gameController);
                response = "Your monster card is destroyed and you received " +
                        calculateDamage(attackerCell, attackedCell) + " battle damage";
                attackerCell.removeCardFromCell(playerGameBoard);
            }
        } else if (attackedCell.getCardStatus() == DEFENSIVE_OCCUPIED) {
            if (isAttackerStronger(attackerCell, attackedCell)) {
                //decreasePlayersDamage(attackerCell, attackedCell);
                response = "the defense position monster is destroyed";
                attackedCell.removeCardFromCell(playerGameBoard);
            } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell))
                response = "no card is destroyed";
            else {
                decreasePlayersDamage(attackerCell, attackedCell, gameController);
                response = "no card is destroyed and you received " +
                        calculateDamage(attackerCell, attackedCell) + " battle damage";
            }
        } else {
            if (isAttackerStronger(attackerCell, attackedCell)) {
                response = "opponent’s monster card was " +
                        attackedCell.getCellCard().getName() + " the defense position monster is destroyed";
                attackedCell.removeCardFromCell(opponentGameBoard);
            } else if (isAttackerAndAttackedPowerEqual(attackerCell, attackedCell))
                response = "opponent’s monster card was " +
                        attackedCell.getCellCard().getName() + " and no card is destroyed";
            else {
                decreasePlayersDamage(attackerCell, attackedCell, gameController);
                response = "opponent’s monster card was " + attackedCell.getCellCard().getName() +
                        " and no card is destroyed and you received " +
                        calculateDamage(attackerCell, attackedCell) + " battle damage";
            }
        }
        return response;
    }

    private void decreasePlayersDamage(Cell attackerCell, Cell attackedCell, GameController gameController) {
        if (isAttackerStronger(attackerCell, attackedCell))
            (gameController.getCurrentTurnOpponentPlayer()).decreaseLP(calculateDamage(attackerCell, attackedCell));
        else
            (gameController.getCurrentTurnPlayer()).decreaseLP(calculateDamage(attackerCell, attackedCell));
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


    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public MonsterAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(MonsterAttribute attribute) {
        this.attribute = attribute;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public static void main(String[] args) {
        Monster monster = new AxeRaider();
    }
}

