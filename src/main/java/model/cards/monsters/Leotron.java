package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class Leotron extends Monster {

    public Leotron() {
        super("Leotron ", "A territorial electronic monster that guards its own domain."
                , 2500, 2000, 0, 4, MonsterAttribute.EARTH, MonsterType.CYBERSE, CardType.NORMAL);
    }

}
