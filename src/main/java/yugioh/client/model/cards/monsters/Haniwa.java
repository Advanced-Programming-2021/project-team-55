package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class Haniwa extends Monster {

    public Haniwa() {
        super("Haniwa", "An earthen figure that protects the tomb of an ancient ruler."
                , 600, 500, 500, 2, MonsterAttribute.EARTH, MonsterType.ROCK, CardType.NORMAL);
    }

}
