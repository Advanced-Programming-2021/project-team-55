package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class Herooftheeast extends Monster {

    public Herooftheeast() {
        super("Hero of the east", "Feel da strength ah dis sword-swinging samurai from da Far East."
                , 1700, 1100, 1000, 3, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
