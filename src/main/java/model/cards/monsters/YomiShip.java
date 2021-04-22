package model.cards.monsters;

import model.cards.MonsterAttribute;
import model.cards.KindOfMonster;

import static model.cards.MonsterAttribute.*;
import static model.cards.KindOfMonster.*;

public class YomiShip {

    private String name = "Yomi Ship";
    private int level = 3;
    private MonsterAttribute attribute = WATER;
    private String monsterType = "Aqua";
    private KindOfMonster kindOfMonster = Effect;
    private int atk = 800;
    private int def = 1400;
    private static final String description = "If this card is destroyed by battle and sent to the GY: Destroy the monster that destroyed this card.";
    private int price = 1700;


}
