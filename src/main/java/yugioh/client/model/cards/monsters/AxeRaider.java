package yugioh.client.model.cards.monsters;

import yugioh.client.model.cards.Monster;
import yugioh.client.model.cards.cardfeaturesenums.CardType;
import yugioh.client.model.cards.cardfeaturesenums.MonsterAttribute;
import yugioh.client.model.cards.cardfeaturesenums.MonsterType;

public class AxeRaider extends Monster {

    public AxeRaider() {
        super("Axe Raider", "An axe-wielding monster of tremendous strength and agility."
                , 3100, 1700, 1150, 4, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
