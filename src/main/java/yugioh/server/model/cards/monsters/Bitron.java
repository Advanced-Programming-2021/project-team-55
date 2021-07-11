package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Bitron extends Monster {

    public Bitron() {
        super("Bitron", "A new species found in electronic space. There's not much information on it."
                , 1000, 200, 2000, 2, MonsterAttribute.EARTH, MonsterType.CYBERSE, CardType.NORMAL);
    }

}
