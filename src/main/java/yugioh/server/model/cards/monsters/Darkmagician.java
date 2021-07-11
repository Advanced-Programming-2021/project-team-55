package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Darkmagician extends Monster {

    public Darkmagician() {
        super("Dark magician", "The ultimate wizard in terms of attack and defense."
                , 8300, 2500, 2100, 7, MonsterAttribute.DARK, MonsterType.SPELLCASTER, CardType.NORMAL);
    }

}
