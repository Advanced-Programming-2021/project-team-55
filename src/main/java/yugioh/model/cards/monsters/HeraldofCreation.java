package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class HeraldofCreation extends Monster {

    public HeraldofCreation() {
        super("Herald of Creation", "Once per turn: You can discard 1 card, then target 1 Level 7 or higher monster in your Graveyard; add that target to your hand."
                , 2700, 1800, 600, 4, MonsterAttribute.LIGHT, MonsterType.SPELLCASTER, CardType.EFFECTIVE);
    }

}
