package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class Haniwa extends Monster {

    public Haniwa() {
        super("Haniwa", "An earthen figure that protects the tomb of an ancient ruler."
                , 600, 500, 500, 2, MonsterAttribute.EARTH, MonsterType.ROCK, CardType.NORMAL);
    }

}
