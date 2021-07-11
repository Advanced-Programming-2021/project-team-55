package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class Fireyarou extends Monster {

    public Fireyarou() {
        super("Fireyarou", "A malevolent creature wrapped in flames that attacks enemies with intense fire."
                , 2500, 1300, 1000, 4, MonsterAttribute.FIRE, MonsterType.PYRO, CardType.NORMAL);
    }

}
