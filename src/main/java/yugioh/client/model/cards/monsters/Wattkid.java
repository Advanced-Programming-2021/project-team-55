package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class Wattkid extends Monster {

    public Wattkid() {
        super("Wattkid", "A creature that electrocutes opponents with bolts of lightning."
                , 1300, 1000, 500, 3, MonsterAttribute.LIGHT, MonsterType.THUNDER, CardType.NORMAL);
    }

}
