package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class TheCalculator extends Monster {

    public TheCalculator() {
        super("The Calculator", "The ATK of this card is the combined Levels of all face-up monsters you control x 300."
                , 8000, 0, 0, 2, MonsterAttribute.LIGHT, MonsterType.THUNDER, CardType.EFFECTIVE);
    }

}
