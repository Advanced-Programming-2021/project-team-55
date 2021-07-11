package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class Herooftheeast extends Monster {

    public Herooftheeast() {
        super("Hero of the east", "Feel da strength ah dis sword-swinging samurai from da Far East."
                , 1700, 1100, 1000, 3, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
