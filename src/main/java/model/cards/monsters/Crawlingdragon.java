package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Crawlingdragon extends Monster {

    public Crawlingdragon() {
        super("Crawling dragon", "This weakened dragon can no longer fly, but is still a deadly force to be reckoned with."
                , 3900, 1600, 1400, 5, MonsterAttribute.EARTH, MonsterType.DRAGON, CardType.NORMAL);
    }

}
