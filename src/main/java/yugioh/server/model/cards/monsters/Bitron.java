package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class Bitron extends Monster {

    public Bitron() {
        super("Bitron", "A new species found in electronic space. There's not much information on it."
                , 1000, 200, 2000, 2, MonsterAttribute.EARTH, MonsterType.CYBERSE, CardType.NORMAL);
    }

}
