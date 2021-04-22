package model.cards.monsters;

import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.CardType;


public class YomiShip {

    private String name = "Yomi Ship";
    private int level = 3;
    private MonsterAttribute attribute = MonsterAttribute.WATER;
    private String monsterType = "Aqua";
    private CardType cardType = CardType.EFFECTIVE;
    private int atk = 800;
    private int def = 1400;
    private static final String description = "If this card is destroyed by battle and sent to the GY: Destroy the monster that destroyed this card.";
    private int price = 1700;


}
