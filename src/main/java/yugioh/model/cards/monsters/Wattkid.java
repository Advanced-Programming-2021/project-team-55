package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class Wattkid extends Monster {

    public Wattkid() {
        super("Wattkid", "A creature that electrocutes opponents with bolts of lightning."
                , 1300, 1000, 500, 3, MonsterAttribute.LIGHT, MonsterType.THUNDER, CardType.NORMAL);
    }

}
