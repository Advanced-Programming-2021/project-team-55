package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class BattleOX extends Monster {

    public BattleOX() {
        super("Battle OX", "A monster with tremendous power, it destroys enemies with a swing of its axe."
                , 2900, 1700, 1000, 4, MonsterAttribute.EARTH, MonsterType.BEAST_WARRIOR, CardType.NORMAL);
    }

}
