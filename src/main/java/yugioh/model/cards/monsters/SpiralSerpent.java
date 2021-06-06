package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class SpiralSerpent extends Monster {

    public SpiralSerpent() {
        super("Spiral Serpent", "When huge whirlpools lay cities asunder, it is the hunger of this sea serpent at work. No one has ever escaped its dreaded Spiral Wave to accurately describe the terror they experienced."
                , 11700, 2900, 2900, 8, MonsterAttribute.WATER, MonsterType.SEA_SERPENT, CardType.NORMAL);
    }

}
