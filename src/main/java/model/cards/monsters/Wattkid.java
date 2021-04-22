package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class Wattkid {

    private String name = "Wattkid";
    private int level = 3;
    private MonsterAttribute attribute = LIGHT;
    private String monsterType = "Thunder";
    private KindOfMonster kindOfMonster = Normal;
    private int atk = 1000;
    private int def = 500;
    private static final String description = "A creature that electrocutes opponents with bolts of lightning.";
    private int price = 1300;


}
