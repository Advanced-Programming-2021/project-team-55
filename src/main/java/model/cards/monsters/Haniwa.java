package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Haniwa extends Monster {

    public Haniwa() {
        super("Haniwa", "An earthen figure that protects the tomb of an ancient ruler."
                , 600, 500, 500, 2, MonsterAttribute.EARTH, MonsterType.ROCK, CardType.NORMAL);
    }

}
