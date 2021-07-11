package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class FeralImp extends Monster {

    public FeralImp() {
        super("Feral Imp", "A playful little fiend that lurks in the dark, waiting to attack an unwary enemy."
                , 2800, 1300, 1400, 4, MonsterAttribute.DARK, MonsterType.FIEND, CardType.NORMAL);
    }

}
