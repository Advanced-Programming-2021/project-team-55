package model.cards.monsters;

import model.cards.cardfeaturesenums.MonsterAttribute;
import model.cards.cardfeaturesenums.CardType;


public class GateGuardian {

    private String name = "Gate Guardian";
    private int level = 11;
    private MonsterAttribute attribute = MonsterAttribute.DARK;
    private String monsterType = "Warrior";
    private CardType cardType = CardType.EFFECTIVE;
    private int atk = 3750;
    private int def = 3400;
    private static final String description = "Cannot be Normal Summoned/Set. Must first be Special Summoned (from your hand) by Tributing 1 \"Sanga of the Thunder\", \"Kazejin\", and \"Suijin\".";
    private int price = 20000;


}
