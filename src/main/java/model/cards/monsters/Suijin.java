package model.cards.monsters;

import model.board.CardStatus;
import model.board.Cell;
import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;
import view.ViewInterface;

public class Suijin extends Monster {

    boolean isEffectUsed = false;

    public Suijin() {
        super("Suijin", "During damage calculation in your opponent's turn, if this card is being attacked: You can target the attacking monster; make that target's ATK 0 during damage calculation only (this is a Quick Effect). This effect can only be used once while this card is face-up on the field."
                , 8700, 2500, 2400, 7, MonsterAttribute.WATER, MonsterType.AQUA, CardType.EFFECTIVE);
    }

    public static void handleEffect(Cell attackerCell, Cell attackedCell){
        if (attackedCell.getCardStatus() != CardStatus.OFFENSIVE_OCCUPIED || !attackedCell.getCellCard().getName().equals("Suijin")) return;

        Suijin attackedMonster = ((Suijin) attackedCell.getCellCard());
        if (attackedMonster.isEffectUsed) return;

        ViewInterface.showResult("Suijin effect activated: " + attackerCell.getCellCard().getName() + "'s ATK will be zero");
        ((Monster) attackerCell.getCellCard()).setAtk(0);
        attackedMonster.isEffectUsed = true;
    }

}
