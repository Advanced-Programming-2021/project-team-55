package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class MirageDragon extends Monster {

    public MirageDragon() {
        super("Mirage Dragon", "Your opponent cannot activate Trap Cards during the Battle Phase."
                , 2500, 1600, 600, 4, MonsterAttribute.LIGHT, MonsterType.DRAGON, CardType.EFFECTIVE);
    }

}