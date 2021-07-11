package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class Crawlingdragon extends Monster {

    public Crawlingdragon() {
        super("Crawling dragon", "This weakened dragon can no longer fly, but is still a deadly force to be reckoned with."
                , 3900, 1600, 1400, 5, MonsterAttribute.EARTH, MonsterType.DRAGON, CardType.NORMAL);
    }

}
