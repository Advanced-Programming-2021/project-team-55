package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class SilverFang extends Monster {

    public SilverFang() {
        super("Silver Fang", "A snow wolf that's beautiful to the eye, but absolutely vicious in battle."
                , 1700, 1200, 800, 3, MonsterAttribute.EARTH, MonsterType.BEAST, CardType.NORMAL);
    }

}
