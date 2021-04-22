package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class Suijin {

    private String name = "Suijin";
    private int level = 7;
    private MonsterAttribute attribute = WATER;
    private String monsterType = "Aqua";
    private KindOfMonster kindOfMonster = Effect;
    private int atk = 2500;
    private int def = 2400;
    private static final String description = "During damage calculation in your opponent's turn, if this card is being attacked: You can target the attacking monster; make that target's ATK 0 during damage calculation only (this is a Quick Effect). This effect can only be used once while this card is face-up on the field.";
    private int price = 8700;


}
