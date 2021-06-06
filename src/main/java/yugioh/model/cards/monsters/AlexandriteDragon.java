package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class AlexandriteDragon extends Monster {

    public AlexandriteDragon() {
        super("Alexandrite Dragon", "Many of the czars' lost jewels can be found in the scales of this priceless dragon. Its creator remains a mystery, along with how they acquired the imperial treasures. But whosoever finds this dragon has hit the jackpot... whether they know it or not."
                , 2600, 2000, 100, 4, MonsterAttribute.LIGHT, MonsterType.DRAGON, CardType.NORMAL);
    }

}
