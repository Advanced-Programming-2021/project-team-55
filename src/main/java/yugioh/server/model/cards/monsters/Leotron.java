package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class Leotron extends Monster {

    public Leotron() {
        super("Leotron ", "A territorial electronic monster that guards its own domain."
                , 2500, 2000, 0, 4, MonsterAttribute.EARTH, MonsterType.CYBERSE, CardType.NORMAL);
    }

}
