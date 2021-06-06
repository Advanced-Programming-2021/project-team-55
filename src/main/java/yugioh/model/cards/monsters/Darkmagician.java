package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class Darkmagician extends Monster {

    public Darkmagician() {
        super("Dark magician", "The ultimate wizard in terms of attack and defense."
                , 8300, 2500, 2100, 7, MonsterAttribute.DARK, MonsterType.SPELLCASTER, CardType.NORMAL);
    }

}
