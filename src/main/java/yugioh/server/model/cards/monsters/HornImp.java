package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class HornImp extends Monster {

    public HornImp() {
        super("Horn Imp", "A small fiend that dwells in the dark, its single horn makes it a formidable opponent."
                , 2500, 1300, 1000, 4, MonsterAttribute.DARK, MonsterType.FIEND, CardType.NORMAL);
    }

}
