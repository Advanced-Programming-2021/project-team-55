package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class Wattaildragon extends Monster {

    public Wattaildragon() {
        super("Wattaildragon", "Capable of indefinite flight. Attacks by wrapping its body with electricity and ramming into opponents.\nIMPORTANT: Capturing the \"Wattaildragon\" is forbidden by the Ancient Rules and is a Level 6 offense, the minimum sentence for which is imprisonment for no less than 2500 heliocycles."
                , 5800, 2500, 1000, 6, MonsterAttribute.LIGHT, MonsterType.DRAGON, CardType.NORMAL);
    }

}
