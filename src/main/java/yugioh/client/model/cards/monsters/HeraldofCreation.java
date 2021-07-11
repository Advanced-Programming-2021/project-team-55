package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class HeraldofCreation extends Monster {

    public HeraldofCreation() {
        super("Herald of Creation", "Once per turn: You can discard 1 card, then target 1 Level 7 or higher monster in your Graveyard; add that target to your hand."
                , 2700, 1800, 600, 4, MonsterAttribute.LIGHT, MonsterType.SPELLCASTER, CardType.EFFECTIVE);
    }

}
