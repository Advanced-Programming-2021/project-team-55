package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class AxeRaider extends Monster {

    public AxeRaider() {
        super("Axe Raider", "An axe-wielding monster of tremendous strength and agility."
                , 3100, 1700, 1150, 4, MonsterAttribute.EARTH, MonsterType.WARRIOR, CardType.NORMAL);
    }

}
