package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Wattkid extends Monster {

    public Wattkid() {
        super("Wattkid", "A creature that electrocutes opponents with bolts of lightning."
                , 1300, 1000, 500, 3, MonsterAttribute.LIGHT, MonsterType.THUNDER, CardType.NORMAL);
    }

}
