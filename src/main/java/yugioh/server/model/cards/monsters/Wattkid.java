package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class Wattkid extends Monster {

    public Wattkid() {
        super("Wattkid", "A creature that electrocutes opponents with bolts of lightning."
                , 1300, 1000, 500, 3, MonsterAttribute.LIGHT, MonsterType.THUNDER, CardType.NORMAL);
    }

}
