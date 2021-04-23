package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Scanner extends Monster {

    public Scanner() {
        super("Scanner", "Once per turn, you can select 1 of your opponent's monsters that is removed from play. Until the End Phase, this card's name is treated as the selected monster's name, and this card has the same Attribute, Level, ATK, and DEF as the selected monster. If this card is removed from the field while this effect is applied, remove it from play."
                , 8000, 0, 0, 1, MonsterAttribute.LIGHT, MonsterType.MACHINE, CardType.EFFECTIVE);
    }

}
