package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class Fireyarou extends Monster {

    public Fireyarou() {
        super("Fireyarou", "A malevolent creature wrapped in flames that attacks enemies with intense fire."
                , 2500, 1300, 1000, 4, MonsterAttribute.FIRE, MonsterType.PYRO, CardType.NORMAL);
    }

}
