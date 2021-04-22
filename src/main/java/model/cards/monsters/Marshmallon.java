package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class Marshmallon {

    private String name = "Marshmallon";
    private int level = 3;
    private MonsterAttribute attribute = LIGHT;
    private String monsterType = "Fairy";
    private KindOfMonster kindOfMonster = Effect;
    private int atk = 300;
    private int def = 500;
    private static final String description = "Cannot be destroyed by battle. After damage calculation, if this card was attacked, and was face-down at the start of the Damage Step: The attacking player takes 1000 damage.";
    private int price = 700;


}
