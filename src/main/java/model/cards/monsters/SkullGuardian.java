package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.CardType;


public class SkullGuardian {

    private String name = "Skull Guardian";
    private int level = 7;
    private MonsterAttribute attribute = MonsterAttribute.LIGHT;
    private String monsterType = "Warrior";
    private CardType cardType = CardType.RITUAL;
    private int atk = 2050;
    private int def = 2500;
    private static final String description = "This monster can only be Ritual Summoned with the Ritual Spell Card, \"Novox's Prayer\". You must also offer monsters whose total Level Stars equal 7 or more as a Tribute from the field or your hand.";
    private int price = 7900;


}
