package model.cards.monsters;

import model.cards.Monster;
import model.cards.cardfeaturesenums.CardType;
import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.MonsterType;

public class YomiShip extends Monster {

    public YomiShip() {
        super("Yomi Ship", "If this card is destroyed by battle and sent to the GY: Destroy the monster that destroyed this card."
                , 1700, 800, 1400, 3, MonsterAttribute.WATER, MonsterType.AQUA, CardType.EFFECTIVE);
    }

}
