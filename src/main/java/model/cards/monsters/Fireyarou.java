package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Fireyarou extends Monster {

    public Fireyarou() {
        super("Fireyarou", "A malevolent creature wrapped in flames that attacks enemies with intense fire."
                , 2500, 1300, 1000, 4, MonsterAttribute.FIRE, MonsterType.PYRO, CardType.NORMAL);
    }

}
