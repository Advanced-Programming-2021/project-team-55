package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class Babydragon extends Monster {

    public Babydragon() {
        super("Baby dragon", "Much more than just a child, this dragon is gifted with untapped power."
                , 1600, 1200, 700, 3, MonsterAttribute.WIND, MonsterType.DRAGON, CardType.NORMAL);
    }

}
