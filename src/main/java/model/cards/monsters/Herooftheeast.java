package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Herooftheeast extends Monster {

    public Herooftheeast() {
        super("Hero of the east", "Feel da strength ah dis sword-swinging samurai from da Far East."
                , 1700, 1100, 1000, 3, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
