package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class SkullGuardian {

    private String name = "Skull Guardian";
    private int level = 7;
    private MonsterAttribute attribute = LIGHT;
    private String monsterType = "Warrior";
    private KindOfMonster kindOfMonster = Ritual;
    private int atk = 2050;
    private int def = 2500;
    private static final String description = "This monster can only be Ritual Summoned with the Ritual Spell Card, "Novox's Prayer". You must also offer monsters whose total Level Stars equal 7 or more as a Tribute from the field or your hand.";
    private int price = 7900;


}
