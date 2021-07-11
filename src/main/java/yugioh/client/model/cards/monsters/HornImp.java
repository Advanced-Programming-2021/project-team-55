package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class HornImp extends Monster {

    public HornImp() {
        super("Horn Imp", "A small fiend that dwells in the dark, its single horn makes it a formidable opponent."
                , 2500, 1300, 1000, 4, MonsterAttribute.DARK, MonsterType.FIEND, CardType.NORMAL);
    }

}
