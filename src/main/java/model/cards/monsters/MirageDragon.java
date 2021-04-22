package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class MirageDragon {

    private String name = "Mirage Dragon";
    private int level = 4;
    private MonsterAttribute attribute = LIGHT;
    private String monsterType = "Dragon";
    private KindOfMonster kindOfMonster = Effect;
    private int atk = 1600;
    private int def = 600;
    private static final String description = "Your opponent cannot activate Trap Cards during the Battle Phase.";
    private int price = 2500;


}
