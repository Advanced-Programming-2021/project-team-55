package model.cards.monsters;

import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.CardType;


public class Suijin {

    private String name = "Suijin";
    private int level = 7;
    private MonsterAttribute attribute = MonsterAttribute.WATER;
    private String monsterType = "Aqua";
    private CardType cardType = CardType.EFFECTIVE;
    private int atk = 2500;
    private int def = 2400;
    private static final String description = "During damage calculation in your opponent's turn, if this card is being attacked: You can target the attacking monster; make that target's ATK 0 during damage calculation only (this is a Quick Effect). This effect can only be used once while this card is face-up on the field.";
    private int price = 8700;


}
