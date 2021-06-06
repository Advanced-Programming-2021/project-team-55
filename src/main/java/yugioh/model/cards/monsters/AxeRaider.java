package yugioh.model.cards.monsters;

import yugioh.model.cards.Monster;
import yugioh.model.cards.cardfeaturesenums.CardType;
import yugioh.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.model.cards.cardfeaturesenums.MonsterType;

public class AxeRaider extends Monster {

    public AxeRaider() {
        super("Axe Raider", "An axe-wielding monster of tremendous strength and agility."
                , 3100, 1700, 1150, 4, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
