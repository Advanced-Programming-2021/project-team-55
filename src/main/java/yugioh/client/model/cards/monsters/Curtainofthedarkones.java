package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class Curtainofthedarkones extends Monster {

    public Curtainofthedarkones() {
        super("Curtain of the dark ones", "A curtain that a spellcaster made, it is said to raise a dark power."
                , 700, 600, 500, 2, MonsterAttribute.DARK, MonsterType.SPELLCASTER, CardType.NORMAL);
    }

}
