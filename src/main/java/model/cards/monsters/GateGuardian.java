package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class GateGuardian {

    private String name = "Gate Guardian";
    private int level = 11;
    private MonsterAttribute attribute = DARK;
    private String monsterType = "Warrior";
    private KindOfMonster kindOfMonster = Effect;
    private int atk = 3750;
    private int def = 3400;
    private static final String description = "Cannot be Normal Summoned/Set. Must first be Special Summoned (from your hand) by Tributing 1 "Sanga of the Thunder", "Kazejin", and "Suijin".";
    private int price = 20000;


}
