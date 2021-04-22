package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class ManEaterBug {

    private String name = "Man-Eater Bug";
    private int level = 2;
    private MonsterAttribute attribute = EARTH;
    private String monsterType = "Insect";
    private KindOfMonster kindOfMonster = Effect;
    private int atk = 450;
    private int def = 600;
    private static final String description = "FLIP: Target 1 monster on the field; destroy that target.";
    private int price = 600;


}
