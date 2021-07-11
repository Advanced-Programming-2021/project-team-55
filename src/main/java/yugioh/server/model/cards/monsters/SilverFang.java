package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class SilverFang extends Monster {

    public SilverFang() {
        super("Silver Fang", "A snow wolf that's beautiful to the eye, but absolutely vicious in battle."
                , 1700, 1200, 800, 3, MonsterAttribute.EARTH, MonsterType.BEAST, CardType.NORMAL);
    }

}
