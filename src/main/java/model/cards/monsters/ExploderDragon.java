package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class ExploderDragon extends Monster {

    public ExploderDragon() {
        super("Exploder Dragon", "If this card is destroyed by battle and sent to the Graveyard: Destroy the monster that destroyed it. Neither player takes any battle damage from attacks involving this attacking card."
                , 1000, 1000, 0, 3, MonsterAttribute.EARTH, MonsterType.DRAGON, CardType.EFFECTIVE);
    }

}
