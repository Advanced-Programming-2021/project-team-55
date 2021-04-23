package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class TheTricky extends Monster {

    public TheTricky() {
        super("The Tricky", "You can Special Summon this card (from your hand) by discarding 1 card."
                , 4300, 2000, 1200, 5, MonsterAttribute.WIND, MonsterType.SPELLCASTER, CardType.EFFECTIVE);
    }

}
