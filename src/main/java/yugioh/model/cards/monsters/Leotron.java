package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class Leotron extends Monster {

    public Leotron() {
        super("Leotron ", "A territorial electronic monster that guards its own domain."
                , 2500, 2000, 0, 4, MonsterAttribute.EARTH, MonsterType.CYBERSE, CardType.NORMAL);
    }

}
