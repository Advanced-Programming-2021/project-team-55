package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class Crawlingdragon extends Monster {

    public Crawlingdragon() {
        super("Crawling dragon", "This weakened dragon can no longer fly, but is still a deadly force to be reckoned with."
                , 3900, 1600, 1400, 5, MonsterAttribute.EARTH, MonsterType.DRAGON, CardType.NORMAL);
    }

}
