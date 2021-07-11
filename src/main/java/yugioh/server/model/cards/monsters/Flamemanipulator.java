package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class Flamemanipulator extends Monster {

    public Flamemanipulator() {
        super("Flame manipulator", "This Spellcaster attacks enemies with fire-related spells such as \"Sea of Flames\" and \"Wall of Fire\"."
                , 1500, 900, 1000, 3, MonsterAttribute.FIRE, MonsterType.SPELLCASTER, CardType.NORMAL);
    }

}
