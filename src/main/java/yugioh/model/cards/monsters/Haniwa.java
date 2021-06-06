package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class Haniwa extends Monster {

    public Haniwa() {
        super("Haniwa", "An earthen figure that protects the tomb of an ancient ruler."
                , 600, 500, 500, 2, MonsterAttribute.EARTH, MonsterType.ROCK, CardType.NORMAL);
    }

}
