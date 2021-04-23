package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class ManEaterBug extends Monster {

    public ManEaterBug() {
        super("Man-Eater Bug", "FLIP: Target 1 monster on the field; destroy that target."
                , 600, 450, 600, 2, MonsterAttribute.EARTH, MonsterType.INSECT, CardType.EFFECTIVE);
    }

}
