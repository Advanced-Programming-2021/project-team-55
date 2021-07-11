package yugioh.server.model.cards.monsters;

import yugioh.server.model.cards.Monster;
import yugioh.server.model.cards.cardfeaturesenums.CardType;
import yugioh.server.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.server.model.cards.cardfeaturesenums.MonsterType;

public class AxeRaider extends Monster {

    public AxeRaider() {
        super("Axe Raider", "An axe-wielding monster of tremendous strength and agility."
                , 3100, 1700, 1150, 4, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
