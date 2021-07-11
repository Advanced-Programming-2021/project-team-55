package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class HeraldofCreation extends Monster {

    public HeraldofCreation() {
        super("Herald of Creation", "Once per turn: You can discard 1 card, then target 1 Level 7 or higher monster in your Graveyard; add that target to your hand."
                , 2700, 1800, 600, 4, MonsterAttribute.LIGHT, MonsterType.SPELLCASTER, CardType.EFFECTIVE);
    }

}
